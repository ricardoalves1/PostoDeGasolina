package com.postoGasolina.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class FormatterSoLetra extends PlainDocument {
	private int quantidadeMaxima;

	public FormatterSoLetra(int maxLenght) {
		super();
		if (maxLenght <= 0)
			throw new IllegalArgumentException("Especifique a quantidade!");
		quantidadeMaxima = maxLenght;
	}

	@Override
	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		// se str n�o for null e o documento n�o tiver mais que "max"
		// caracteres,
		// insere o novo caracter
		if (str != null && getLength() < quantidadeMaxima)
			super.insertString(offset, str.replaceAll("[^a-z|^A-Z|^ ��������������������������]", ""), attr);
	}
}
