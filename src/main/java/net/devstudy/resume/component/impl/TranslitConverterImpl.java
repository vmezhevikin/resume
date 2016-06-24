package net.devstudy.resume.component.impl;

import org.springframework.stereotype.Component;

import net.devstudy.resume.component.TranslitConverter;
import net.sf.junidecode.Junidecode;

@Component
public class TranslitConverterImpl implements TranslitConverter {
	
	@Override
	public String translit(String text) {
		return Junidecode.unidecode(text).replace(" ", "");
	}
}