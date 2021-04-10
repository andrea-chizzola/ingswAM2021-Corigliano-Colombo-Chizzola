package it.polimi.ingsw.xmlParser;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import it.polimi.ingsw.*;
import org.w3c.dom.*;

import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;

import org.xml.sax.SAXException;

import static java.lang.Integer.parseInt;

/**
 * This class is responsible of the creation of DevelopmentCard and LeaderCard from a given XML file.
 * In order to build the right type of Resource or cardColor, the class leverage two maps.
 * This class uses the Singleton design pattern
 */

public class CardParser {
    /**
     * this attribute is the map of resources
     */
    private static Map<String, Supplier<Resource>> resources;
    /**
     * this attribute is the map of cardColors
     */
    private static Map<String, Supplier<CardColor>> cardColor;
    /**
     * this attribute is the instance of the Singleton pattern
     */
    private static CardParser instance;

    /**
     * the constructor of CardParser is private and initializes the maps
     */
    private CardParser() {

        resources = new HashMap<>();
        cardColor = new HashMap<>();
        cardColor.put("GREEN", Green::new);
        cardColor.put("BLUE", Blue::new);
        cardColor.put("YELLOW", Yellow::new);
        cardColor.put("PURPLE", Purple::new);

        resources.put("COINS", Coin::new);
        resources.put("SERVANTS", Servant::new);
        resources.put("STONES", Stone::new);
        resources.put("FAITH", Faith::new);
        resources.put("SHIELDS", Shield::new);
    }

    /**
     * this method is used to get an instance of CardParser.
     *
     * @return a new instance of CardParser if instance == null. If a CardParser has already been created, the method
     * return instance.
     */
    public static CardParser instance() {
        if (instance == null) {
            instance = new CardParser();
        }
        return instance;
    }

    /**
     * this function is used to create a set of DevelopmentCards from a given file
     *
     * @param file the source XML file
     * @return the LinkedList of DevelopmentCards created using the file
     */

    public List<DevelopmentCard> parseDevelopmentCard(String file) {

        List<DevelopmentCard> newCards = new ArrayList<>();

        try {
            NodeList cards = getCards(file);

            for (int i = 0; i < cards.getLength(); i++) {
                Node card = cards.item(i);
                if (card.getNodeType() == Node.ELEMENT_NODE) {
                    newCards.add(buildDevelopmentCard((Element) card));
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return newCards;
    }

    /**
     *
     * @param card is the XML element that contains the characteristics of a DevelopmentCard
     * @return a new DevelopmentCard with the given characteristics
     */
    private DevelopmentCard buildDevelopmentCard(Element card) {

        int victoryPoint = getPoints(card);
        int level = getLevel(card);
        DevColor color = getCardColor(card).getColor();

        ResourceReqDev requirement = buildRequirementDev((Element) card.getElementsByTagName("requirements").item(0));
        Production effect = buildProduction((Element) card.getElementsByTagName("production").item(0));

        return new DevelopmentCard(victoryPoint, effect, requirement, color, level);
    }

    /**
     *
     * @param requirement an XML element containing all the requirements of a DevelopmentCard
     * @return a ResourceReqDev with the given characteristics
     */
    private ResourceReqDev buildRequirementDev(Element requirement) {
        return new ResourceReqDev(createResourceList(requirement));
    }

    /**
     *
     * @param production an XML element containing all the requirements and the resulting products of a production
     * @return a Production with the given characteristics
     */

    private Production buildProduction(Element production) {

        LinkedList<ResQuantity> materials = createResourceList((Element) production.getElementsByTagName("materials").item(0));
        LinkedList<ResQuantity> products = createResourceList((Element) production.getElementsByTagName("products").item(0));
        return new Production(materials, products);
    }

    /**
     *
     * @param req an XML element which contains the name of a resource
     * @return an instance of the given resource
     */

    private Resource getRes(Element req) {
        String type = req.getAttribute("resource").toUpperCase();
        return resources.get(type).get();
    }

    /**
     *
     * @param el an XML attribute that contains a list of resources and amount of resources
     * @return a list of ResQuantity containing that information.
     */
    private LinkedList<ResQuantity> createResourceList(Element el) {
        LinkedList<ResQuantity> resList = new LinkedList<>();
        NodeList resChild = el.getChildNodes();

        for (int i = 0; i < resChild.getLength(); i++) {
            Node resNode = resChild.item(i);
            if (resNode.getNodeType() == Node.ELEMENT_NODE) {
                Element res = (Element) resNode;
                resList.add(buildResQuantity(res));
            }
        }
        return resList;
    }

    private ResQuantity buildResQuantity(Element el){
        return new ResQuantity(getRes(el), getAmount(el));
    }

    /**
     *
     * @param req a XML element that contains an amount
     * @return an int of the value of amount
     */

    //per implementare effetti e requisiti strani usa if uno dopo l'altro che si attivano sse "hasAttribute".
    private int getAmount(Element req) {
        return parseInt(req.getAttribute("amount"));
    }

    /**
     *
     * @param card a XML element that contains the number of points
     * @return an int that represents the number of points
     */
    private int getPoints(Element card) {
        return parseInt(card.getAttribute("points"));
    }

    /**
     *
     * @param card a XML element that contains the level of a card
     * @return an int that represents the level of the given card
     */

    private int getLevel(Element card) {
        return parseInt(card.getAttribute("level"));
    }

    /**
     *
     * @param card an XML element that contains the color of a card
     * @return an instance of CardColor of the given color
     */
    private CardColor getCardColor(Element card) {
        return cardColor.get(card.getAttribute("color")).get();
    }

    /**
     *
     * this method opens a file and creates a nodeList containing the cards that we want to parse.
     * @param file is the name of the file that contains the cards
     * @return the NodeList of cards
     * @throws ParserConfigurationException if a DocumentBuild cannot be created
     * @throws SAXException if any parse errors occur
     * @throws IOException if any IO errors occur
     */
    private NodeList getCards(String file) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.parse(new File(file));
        Element root = document.getDocumentElement();
        return root.getChildNodes();
    }

    /**
     * this function is used to create a set of LeaderCards from a given file
     *
     * @param file the source XML file
     * @return the LinkedList of LeaderCards created using the file
     */
    public List<LeaderCard> parseLeaderCard(String file) {

        List<LeaderCard> newCards = new ArrayList<>();

        try {
            NodeList cards = getCards(file);

            for (int i = 0; i < cards.getLength(); i++) {
                Node card = cards.item(i);
                if (card.getNodeType() == Node.ELEMENT_NODE) {
                    newCards.add(buildLeaderCard((Element) card));
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return newCards;
    }

    /**
     *
     * @param card is the XML element that contains the characteristics of a LeaderCard
     * @return a new LeaderCard with the given characteristics
     */
    private LeaderCard buildLeaderCard(Element card) {

        int victoryPoint = getPoints(card);

        Requirements requirement = buildRequirementLeader((Element) card.getElementsByTagName("requirements").item(0));
        SpecialEffect effect = buildEffectLeader((Element) card.getElementsByTagName("effect").item(0));

        return new LeaderCard(victoryPoint, effect, requirement);
    }

    /**
     *
     * @param effect an XML element containing the type of effect
     * @return an effect of the given type with the given characteristics
     */
    private SpecialEffect buildEffectLeader(Element effect) {
        if(effect.getElementsByTagName("discount").getLength()>0){
            return buildDiscount((Element) effect.getElementsByTagName("discount").item(0));
        }
        if(effect.getElementsByTagName("whiteMarble").getLength()>0){
            return buildWhiteMarble((Element) effect.getElementsByTagName("whiteMarble").item(0));
        }
        if(effect.getElementsByTagName("production").getLength()>0){
            return buildProduction((Element) effect.getElementsByTagName("production").item(0));
        }
        return buildExtraSlot((Element) effect.getElementsByTagName("extraSlot").item(0));
    }

    /**
     *
     * @param requirement an XML element containing the type of requirement
     * @return a requirement of the given type with the given characteristics
     */
    private Requirements buildRequirementLeader(Element requirement){
        if(requirement.getElementsByTagName("cardRequirement").getLength()>0){
            return buildRequirementCards((Element) requirement.getElementsByTagName("cardRequirement").item(0));
        }
        else return buildRequirementRes((Element) requirement.getElementsByTagName("resourceRequirement").item(0));
    }

    /**
     *
     * @param discount is an XML element containing the characteristics of a SpecialEffect Discount
     * @return an object Discount with the given characteristics
     */
    private Discount buildDiscount(Element discount){
        Element resQuantity = (Element) discount.getElementsByTagName("resquantity").item(0);
        return new Discount(buildResQuantity(resQuantity));
    }

    /**
     *
     * @param marble is an XML element containing the characteristics of a SpecialEffect WhiteMarble
     * @return an object WhiteMarble with the given characteristics
     */
    private WhiteMarble buildWhiteMarble(Element marble){
        return new WhiteMarble(getRes(marble));
    }

    /**
     *
     * @param extra is an XML element containing the characteristics of a SpecialEffect ExtraSlot
     * @return an object ExtraSlot with the given characteristics
     */
    private ExtraSlot buildExtraSlot(Element extra){
        Element resQuantity = (Element) extra.getElementsByTagName("resquantity").item(0);
        return new ExtraSlot(buildResQuantity(resQuantity));
    }

    /**
     *
     * @param card is an XML element containing the characteristics of a Requirements CardRequirements
     * @return an object CardRequirements with the given characteristics
     */
    private CardRequirements buildRequirementCards(Element card){
        return new CardRequirements( createCardList(card) );
    }

    /**
     *
     * @param cards is an XML element containing the characteristics of a Requirements CardRequirements
     * @return a list of CardQuantity containing the requirements
     */
    private LinkedList<CardQuantity> createCardList(Element cards){
        LinkedList<CardQuantity> cardList = new LinkedList<>();
        NodeList cardChild = cards.getChildNodes();

        for (int i = 0; i < cardChild.getLength(); i++) {
            Node cardNode = cardChild.item(i);
            if (cardNode.getNodeType() == Node.ELEMENT_NODE) {
                Element card = (Element) cardNode;
                cardList.add(buildCardQuantity(card));
            }
        }
        return cardList;
    }

    /**
     *
     * @param card is an XML element that contains the level, the color, and the number of cards
     * @return an object CardQuantity with the given characteristics
     */
    private CardQuantity buildCardQuantity(Element card){
        return new CardQuantity(getCardColor(card), getAmount(card), getLevel(card));
    }

    /**
     *
     * @param resources is an XML element that contains the characteristics of a ResourceReqLeader
     * @return an object ResourceReqLead with the given chracteristics
     */
    private ResourceReqLeader buildRequirementRes(Element resources){
        return new ResourceReqLeader(createResourceList(resources));
    }
}