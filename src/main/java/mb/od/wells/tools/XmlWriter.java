package mb.od.wells.tools;

import mb.od.wells.entity.Equipment;
import mb.od.wells.view.Render;
import mb.od.wells.view.WellDataModel;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlWriter {
    private String dataFile;
    private List<WellDataModel> wellsData;

    public void setDataFile(String fileName) {
        this.dataFile = fileName;
        Render render = new Render();
        wellsData = render.makeWellsData();
    }

    public void saveDataFile() throws Exception {
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(dataFile));

        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");

        StartDocument startDocument = eventFactory.createStartDocument();
        eventWriter.add(startDocument);
        eventWriter.add(end);
        StartElement dataStartElement = eventFactory.createStartElement("", "", "dbinfo");
        eventWriter.add(dataStartElement);
        eventWriter.add(end);

        for (WellDataModel wd : wellsData) {
            Map<String, String> well_attrs = new HashMap<>();
            well_attrs.put("name", wd.getName());
            well_attrs.put("id", Integer.toString(wd.getId()));

            List<Child> children = new ArrayList<>();
            for ( Equipment eq : wd.getEquipments() ) {

                Child child = new Child();
                child.setName("equipment");
                child.setParent("well");

                Map<String, String> eq_attrs = new HashMap<>();
                eq_attrs.put("name", eq.getName());
                eq_attrs.put("id", Integer.toString(eq.getId()));
                child.setAttributes(eq_attrs);

                children.add(child);
            }
            createNode(eventWriter, "well", well_attrs, "", children);
        }

        eventWriter.add(eventFactory.createEndElement("", "", "dbinfo"));
        eventWriter.add(end);
        eventWriter.add(eventFactory.createEndDocument());
        eventWriter.close();
    }

    private void createNode(
            XMLEventWriter eventWriter,
            String name,
            Map<String, String> attributes,
            String value,
            List<Child> children

    ) throws XMLStreamException {
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");
        StartElement sElement = eventFactory.createStartElement("", "", name);
        eventWriter.add(tab);
        eventWriter.add(sElement);

        for(Map.Entry<String, String> entry : attributes.entrySet()){
            Attribute attribute = eventFactory.createAttribute(entry.getKey(), entry.getValue());
            eventWriter.add(attribute);
        }
        Characters characters = eventFactory.createCharacters(value);
        eventWriter.add(characters);

        if(!children.isEmpty()) {
            eventWriter.add(end);
            for( Child child : children ) {
                createNode(eventWriter, child.getName(), child.getAttributes(), "");
            }
            eventWriter.add(tab);
        }

        EndElement eElement = eventFactory.createEndElement("", "", name);
        eventWriter.add(eElement);
        eventWriter.add(end);
    }

    private void createNode(
            XMLEventWriter eventWriter,
            String name,
            Map<String, String> attributes,
            String value
    ) throws XMLStreamException {
        List<Child> children = new ArrayList<>();
        createNode(eventWriter, name, attributes, value, children);
    }


    private class Child {
        private String name;
        private String parent;
        private String value;
        private Map<String, String> attributes;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParent() {
            return parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Map<String, String> getAttributes() {
            return attributes;
        }

        public void setAttributes(Map<String, String> attributes) {
            this.attributes = attributes;
        }
    }

}
