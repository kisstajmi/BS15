package com.nng.bs15;

enum KEYS {
	KEY_0('0'), KEY_1('1'), KEY_2('2'), KEY_3('3'), KEY_4('4'), KEY_5('5'), KEY_6('6'), KEY_7('7'), KEY_8('8'), KEY_9('9'),

	KEY_q('q'), KEY_w('w'), KEY_e('e'), KEY_r('r'), KEY_t('t'), KEY_z('z'), KEY_u('u'), KEY_i('i'), KEY_o('o'), KEY_p('p'),

	KEY_a('a'), KEY_s('s'), KEY_d('d'), KEY_f('f'), KEY_g('g'), KEY_h('h'), KEY_j('j'), KEY_k('k'), KEY_l('l'), KEY_ENTER('\n', "ENTER"),

	KEY_SHIFT(null, "SHIFT"), KEY_y('y'), KEY_x('x'), KEY_c('c'), KEY_v('v'), KEY_b('b'), KEY_n('n'), KEY_m('m'), KEY_DOT('.'), KEY_BACKSPACE(null,
			"BACKSPACE"),

	KEY_SPACE(' ', "SPACE");

	char	value;
	String	text;

	private KEYS(char value) {
		this(value, String.valueOf(value));
	}

	private KEYS(Character keyValue, String keyText) {
		if (keyValue != null) {
			value = keyValue;
		}
		text = keyText;
	}

	public char getValue() {
		return value;
	}

	public String getText() {
		return text;
	}

	public static KEYS toKey(String attribute) {
		for (KEYS key : KEYS.values()) {
			if (attribute.equalsIgnoreCase(key.text)) {
				return key;
			}
		}
		return null;
	}
}