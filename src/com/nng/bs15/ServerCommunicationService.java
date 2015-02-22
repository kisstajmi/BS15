package com.nng.bs15;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.UnknownHostException;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EService;
import org.apache.commons.net.telnet.TelnetClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

@EService
public class ServerCommunicationService extends IntentService {

	private String	port		= "";
	private String	serverIP	= "";
	// private String testID = "NNG";
	// private String testID = "TEST1";
	private String	testID		= "TEST2";
	// private String testID = "TEST3";
	// private String testID = "TEST4";
	@App
	BS15Application	app;
	BufferedReader	br;
	PrintStream		out;

	public ServerCommunicationService() {
		this(ServerCommunicationService.class.getSimpleName());
	}

	public ServerCommunicationService(String name) {
		super(name);
	}

	@AfterInject
	protected void afterInject() {
		port = app.getString(R.string.server_port);
		serverIP = app.getString(R.string.server_ip_address);
		serverIP = "192.168.1.105";
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		if (intent.hasExtra(getString(R.string.sel_server_ip_intent))) {
			serverIP = intent.getStringExtra(getString(R.string.sel_server_ip_intent));
		}
		if (intent.hasExtra(getString(R.string.sel_server_port_intent))) {
			port = intent.getStringExtra(getString(R.string.sel_server_port_intent));
		}
		connect(serverIP, Integer.valueOf(port));
	}

	public void connect(String ip, int port) {

		TelnetClient telnet = new TelnetClient();

		String response = "";

		try {
			telnet.connect(ip, port);

			br = new BufferedReader(new InputStreamReader(telnet.getInputStream()));
			out = new PrintStream(telnet.getOutputStream());

			// Log the user on
			readUntil("BSP 1.0 SERVER HELLO");
			write("BSP 1.0 CLIENT HELLO");
			readUntil("SEND YOUR ID");
			write(testID);
			readUntil(testID + " ID ACK - WAITING FOR REQUEST");
			write("RQSTDATA");
			String password = br.readLine();
			if (password != null && password.startsWith("PASSWORD=")) {
				password = password.replace("PASSWORD=", "");
				app.setUserPass(password);
			}
			write("RQSTTRAIN");
			readandParseTrainXML();
			write("RQSTTEST");
			readandParseTestXML();

		} catch (UnknownHostException e) {
			e.printStackTrace();
			response = "UnknownHostException: " + e.toString();
		} catch (IOException e) {
			e.printStackTrace();
			response = "IOException: " + e.toString();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				out.close();
			}
			if (telnet != null) {
				try {
					telnet.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			stopSelf();
			Log.d(ServerCommunicationService.class.getSimpleName(), "RESPONSE: " + response);
		}
	}

	private void readandParseTrainXML() throws IOException {
		String traindata = readUntil("</training>");

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dbFactory.setNamespaceAware(true);
			dBuilder = dbFactory.newDocumentBuilder();

			Document doc = dBuilder.parse(new ByteArrayInputStream(traindata.getBytes()));

			doc.getDocumentElement().normalize();
			NodeList patternList = doc.getElementsByTagName("pattern");
			Vector<PatternValue> pvVector = new Vector<PatternValue>();
			for (int i = 0; i < patternList.getLength(); i++) {
				Node patternNode = patternList.item(i);
				pvVector.add(Learn(parsePattern(patternNode)));
			}
			app.addLearnedData(makeAVG(pvVector));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}

	private LearnedValue makeAVG(Vector<PatternValue> pvVector) {
		PatternValue max = new PatternValue(pvVector.get(0));
		PatternValue min = new PatternValue(pvVector.get(0));
		PatternValue avg = new PatternValue(pvVector.get(0));
		pvVector.remove(0);
		for (PatternValue act : pvVector) {
			CalculateMaxMin(act, max, min, avg);
		}
		return DivideAvg(pvVector.size() + 1, avg, min, max);
	}

	@SuppressLint("UseValueOf")
	private LearnedValue DivideAvg(int size, PatternValue avg, PatternValue min, PatternValue max) {
		LearnedValue learnedValue = new LearnedValue();
		float fsize = new Float(size);
		learnedValue.testTime = Math.round(new Float(avg.testTime) / fsize);
		learnedValue.keyNum = Math.round(new Float(avg.keyNum) / fsize);
		learnedValue.specNum = Math.round(new Float(avg.specNum) / fsize);
		learnedValue.KeyPress = Math.round(new Float(avg.KeyPress) / fsize);
		learnedValue.keyTime = Math.round(new Float(avg.keyTime) / fsize);
		learnedValue.diffTestTime = (max.testTime - min.testTime) / 2;
		learnedValue.diffKeyNum = (max.keyNum - min.keyNum) / 2;
		learnedValue.diffSpecNum = (max.specNum - min.specNum) / 2;
		learnedValue.diffAvgKeyPress = (max.KeyPress - min.KeyPress) / 2;
		learnedValue.diffAvgkeyTime = (max.keyTime - min.keyTime) / 2;
		Log.d("learndata", learnedValue.toString());
		return learnedValue;
	}

	private void CalculateMaxMin(PatternValue act, PatternValue max, PatternValue min, PatternValue avg) {
		if (act.testTime > max.testTime)
			max.testTime = act.testTime;
		else if (act.testTime < min.testTime)
			min.testTime = act.testTime;
		avg.testTime += act.testTime;

		if (act.keyNum > max.keyNum)
			max.keyNum = act.keyNum;
		else if (act.keyNum < min.keyNum)
			min.keyNum = act.keyNum;
		avg.keyNum += act.keyNum;

		if (act.specNum > max.specNum)
			max.specNum = act.specNum;
		else if (act.specNum < min.specNum)
			min.specNum = act.specNum;
		avg.specNum += act.specNum;

		if (act.KeyPress > max.KeyPress)
			max.KeyPress = act.KeyPress;
		else if (act.KeyPress < min.KeyPress)
			min.KeyPress = act.KeyPress;
		avg.KeyPress += act.KeyPress;

		if (act.keyTime > max.keyTime)
			max.keyTime = act.keyTime;
		else if (act.keyTime < min.keyTime)
			min.keyTime = act.keyTime;
		avg.keyTime += act.keyTime;
	}

	private PatternValue Learn(Vector<UserKeyPressData> parsePattern) {
		UserKeyPressData prevdata;
		UserKeyPressData actdata;
		StringBuilder testPassword = new StringBuilder();
		PatternValue pv = new PatternValue();
		for (int i = 1; i < parsePattern.size(); i++) {
			prevdata = parsePattern.get(i - 1);
			actdata = parsePattern.get(i);
			if (!isSpecial(actdata.code)) {
				pv.testTime = actdata.time;
				if (actdata.isup) {
					testPassword.append(actdata.code);
					pv.keyNum++;
					pv.KeyPress += (actdata.time - prevdata.time);
				} else {
					pv.keyTime += (actdata.time - prevdata.time);
				}
			} else {
				pv.specNum++;
				if (actdata.code.equals("BACKSPACE") && testPassword.length() > 0) {
					testPassword.deleteCharAt(testPassword.length() - 1);
				}
			}
			prevdata = actdata;
		}
		pv.KeyPress = Math.round((float) pv.KeyPress / (float) pv.keyNum);
		pv.keyTime = Math.round((float) pv.keyTime / (float) pv.keyNum);
		return pv;
	}

	private Vector<UserKeyPressData> parsePattern(Node patternNode) {
		Vector<UserKeyPressData> ukpd = new Vector<UserKeyPressData>();
		if (patternNode != null && patternNode.getNodeType() == Node.ELEMENT_NODE) {
			Element patternElement = (Element) patternNode;
			NodeList keyeventList = patternElement.getChildNodes();
			for (int j = 0; j < keyeventList.getLength(); j++) {
				Node keyeventNode = keyeventList.item(j);
				if (keyeventNode.getNodeType() == Node.ELEMENT_NODE) {
					Element keyeventElement = (Element) keyeventNode;
					int relativeposx = Integer.valueOf(keyeventElement.getAttribute("relative-pos-x").replace("%", ""));
					int relativeposy = Integer.valueOf(keyeventElement.getAttribute("relative-pos-y").replace("%", ""));
					boolean isup = keyeventElement.getTagName().equals("key-up");
					int time = Integer.valueOf(keyeventElement.getAttribute("posix-time"));
					String code = keyeventElement.getAttribute("code");
					Log.d("data", "code: " + code + " time: " + time + " up: " + isup);
					ukpd.add(new UserKeyPressData(relativeposx, relativeposy, isup, code, time));
				}
			}
		}
		return ukpd;
	}

	private void readandParseTestXML() throws IOException {
		String pattern = "";
		while ((pattern = readUntil("</pattern>", "GOODBYE")) != null && !pattern.contains("GOODBYE")) {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			try {
				dbFactory.setNamespaceAware(true);
				dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(new ByteArrayInputStream(pattern.getBytes()));
				doc.getDocumentElement().normalize();
				Node patternNode = doc.getDocumentElement();
				if (makeDecision(parsePattern(patternNode))) {
					write("ACCEPT");
					Log.d("ACCEPTANCE", "ACCEPT");
				} else {
					write("REJECT");
					Log.d("ACCEPTANCE", "REJECT");
				}
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			}

		}
		Log.d("ACCEPTANCE", pattern);
	}

	private boolean makeDecision(Vector<UserKeyPressData> parsePattern) {
		UserKeyPressData prevdata;
		UserKeyPressData actdata;
		StringBuilder testPassword = new StringBuilder();
		PatternValue pv = new PatternValue();
		for (int i = 1; i < parsePattern.size(); i++) {
			prevdata = parsePattern.get(i - 1);
			actdata = parsePattern.get(i);
			if (!isSpecial(actdata.code)) {
				pv.testTime = actdata.time;
				if (actdata.isup) {
					testPassword.append(actdata.code);
					pv.keyNum++;
					pv.KeyPress += (actdata.time - prevdata.time);
				} else {
					pv.keyTime += (actdata.time - prevdata.time);
				}
			} else {
				pv.specNum++;
				if (actdata.code.equals("BACKSPACE") && testPassword.length() > 0) {
					testPassword.deleteCharAt(testPassword.length() - 1);
				}
			}
			prevdata = actdata;
		}
		pv.KeyPress = Math.round((float) pv.KeyPress / (float) pv.keyNum);
		pv.keyTime = Math.round((float) pv.keyTime / (float) pv.keyNum);
		return app.getUserPass().equals(testPassword.toString()) && app.getLearnedValue().matches(pv);
	}

	private boolean isSpecial(String code) {
		return code.equals("ENTER") || code.equals("BACKSPACE");
	}

	boolean	matchpattern	= false;

	private String readUntil(String... pattern) throws IOException {
		String total = "";
		String x = "";
		while ((x = br.readLine()) != null) {
			total += x;
			// Log.d(ServerCommunicationService.class.getSimpleName(),
			// "RESPONSE: " + x);
			// Log.d(ServerCommunicationService.class.getSimpleName(),
			// "matchpattern: " + machPatterns(x, pattern));
			if (machPatterns(x, pattern)) {
				break;
			}
		}
		Log.d(ServerCommunicationService.class.getSimpleName(), "RESPONSE: " + total);
		return total.trim();
	}

	private boolean machPatterns(String total, String[] pattern) {
		if (pattern.length > 1) {
			return total.endsWith(pattern[0]) ? true : total.contains(pattern[1]);
		} else if (pattern.length > 0) {
			return total.endsWith(pattern[0]);
		}
		return false;
	}

	public void write(String value) {
		Log.d(ServerCommunicationService.class.getSimpleName(), "ANSWERE: " + value);
		try {
			out.println(value);
			out.flush();
			System.out.println(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
