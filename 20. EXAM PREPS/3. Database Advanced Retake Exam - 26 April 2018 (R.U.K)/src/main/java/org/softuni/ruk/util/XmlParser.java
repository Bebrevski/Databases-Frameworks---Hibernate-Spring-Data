package org.softuni.ruk.util;

import javax.xml.bind.JAXBException;

public interface XmlParser {
    <O> O parseXml(Class<O> onjectClass, String filePath) throws JAXBException;
}
