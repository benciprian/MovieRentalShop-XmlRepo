package ro.ubb.xml.Repository;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.SAXException;
import ro.ubb.xml.Domain.Movie;
import ro.ubb.xml.Domain.Validators.Validator;
import ro.ubb.xml.Domain.Validators.ValidatorException;
import ro.ubb.xml.Repository.InMemoryRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieFileXmlRepository extends InMemoryRepository<Long, Movie> {
    private String fileName;

    public MovieFileXmlRepository(Validator<Movie> validator, String fileName) {
        super(validator);
        this.fileName = fileName;

        loadData();
    }

    @Override
    public Optional<Movie> save(Movie entity) throws ValidatorException {
        Optional<Movie> optional = super.save(entity);
        if (optional.isEmpty()) {
            saveData();
        }
        return optional;
    }

    @Override
    public Optional<Movie> delete(Long id) {
        Optional<Movie> optional = super.delete(id);
        if (optional.isPresent()) {
            saveData();
        }
        return optional;
    }

    @Override
    public Optional<Movie> update(Movie entity) throws ValidatorException {
        Optional<Movie> optional = super.update(entity);
        if (optional.isPresent()) {
            saveData();
        }
        return optional;
    }

    private void saveData() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element rootElement = document.createElement("MovieRentalShop");
            document.appendChild(rootElement);

            for (Movie movie : entities.values()) {
                Element movieElement = document.createElement("Movie");

                Element idElement = document.createElement("id");
                idElement.appendChild(document.createTextNode(String.valueOf(movie.getId())));
                movieElement.appendChild(idElement);

                Element titleElement = document.createElement("title");
                titleElement.appendChild(document.createTextNode(movie.getTitle()));
                movieElement.appendChild(titleElement);

                Element yearElement = document.createElement("year");
                yearElement.appendChild(document.createTextNode(String.valueOf(movie.getYear())));
                movieElement.appendChild(yearElement);

                Element genreElement = document.createElement("genre");
                genreElement.appendChild(document.createTextNode(movie.getGenre()));
                movieElement.appendChild(genreElement);

                Element rentalPriceElement = document.createElement("rentalPrice");
                rentalPriceElement.appendChild(document.createTextNode(String.valueOf(movie.getRentalPrice())));
                movieElement.appendChild(rentalPriceElement);

                rootElement.appendChild(movieElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new FileOutputStream(fileName));

            transformer.transform(source, result);
        } catch (ParserConfigurationException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(fileName));
            Element rootElement = document.getDocumentElement();
            NodeList movieNodes = rootElement.getElementsByTagName("Movie");

            for (int i = 0; i < movieNodes.getLength(); i++) {
                Element movieElement = (Element) movieNodes.item(i);

                Long id = Long.parseLong(getTextContentFromTag("id", movieElement));
                String title = getTextContentFromTag("title", movieElement);
                int year = Integer.parseInt(getTextContentFromTag("year", movieElement));
                String genre = getTextContentFromTag("genre", movieElement);
                double rentalPrice = Double.parseDouble(getTextContentFromTag("rentalPrice", movieElement));

                Movie movie = new Movie(id, title, year, genre, rentalPrice);
                super.save(movie);
            }
        } catch (ParserConfigurationException | IOException | SAXException | ValidatorException e) {
            e.printStackTrace();
        }
    }

    private static String getTextContentFromTag(String tagName, Element movieElement) {
        NodeList tagList = movieElement.getElementsByTagName(tagName);
        Node elementNode = tagList.item(0);
        return elementNode.getTextContent();
    }
}
