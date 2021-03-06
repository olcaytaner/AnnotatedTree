package AnnotatedTree;

import AnnotatedSentence.ViewLayerType;
import ParseTree.ParseNode;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.ArrayList;

public class ParseNodeSearchable extends ParseNode {

    private ArrayList<SearchType> searchTypes;
    private ArrayList<ViewLayerType> viewLayerTypes;
    private ArrayList<String> searchValues;
    private boolean isLeaf = false;

    public ParseNodeSearchable(ParseNodeSearchable parent, Node node){
        NamedNodeMap attributes;
        Node child;
        children = new ArrayList<ParseNode>();
        this.parent = parent;
        isLeaf = node.getNodeName().equalsIgnoreCase("leaf");
        searchTypes = new ArrayList<>();
        viewLayerTypes = new ArrayList<>();
        searchValues = new ArrayList<>();
        if (node.hasAttributes()){
            attributes = node.getAttributes();
            for (int i = 0; i < attributes.getLength(); i++){
                Node attribute = attributes.item(i);
                String viewLayerType = attribute.getNodeName().substring(0, 3);
                String searchType = attribute.getNodeName().substring(3);
                searchValues.add(attribute.getNodeValue());
                if (searchType.equalsIgnoreCase("equals")){
                    searchTypes.add(SearchType.EQUALS);
                } else {
                    if (searchType.equalsIgnoreCase("contains")){
                        searchTypes.add(SearchType.CONTAINS);
                    } else {
                        if (searchType.equalsIgnoreCase("matches")){
                            searchTypes.add(SearchType.MATCHES);
                        } else {
                            if (searchType.equalsIgnoreCase("starts")){
                                searchTypes.add(SearchType.STARTS);
                            } else {
                                if (searchType.equalsIgnoreCase("ends")){
                                    searchTypes.add(SearchType.ENDS);
                                } else {
                                    if (searchType.equalsIgnoreCase("equalsignorecase")){
                                        searchTypes.add(SearchType.EQUALS_IGNORE_CASE);
                                    } else {
                                        if (searchType.equalsIgnoreCase("isnull")){
                                            searchTypes.add(SearchType.IS_NULL);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (viewLayerType.equalsIgnoreCase("mor") || viewLayerType.equalsIgnoreCase("inf")){
                    viewLayerTypes.add(ViewLayerType.INFLECTIONAL_GROUP);
                } else {
                    if (viewLayerType.equalsIgnoreCase("tur")){
                        viewLayerTypes.add(ViewLayerType.TURKISH_WORD);
                    } else {
                        if (viewLayerType.equalsIgnoreCase("per")){
                            viewLayerTypes.add(ViewLayerType.PERSIAN_WORD);
                        } else {
                            if (viewLayerType.equalsIgnoreCase("eng")){
                                viewLayerTypes.add(ViewLayerType.ENGLISH_WORD);
                            } else {
                                if (viewLayerType.equalsIgnoreCase("ner")){
                                    viewLayerTypes.add(ViewLayerType.NER);
                                } else {
                                    if (viewLayerType.equalsIgnoreCase("sem") || viewLayerType.equalsIgnoreCase("tse")){
                                        viewLayerTypes.add(ViewLayerType.SEMANTICS);
                                    } else {
                                        if (viewLayerType.equalsIgnoreCase("met")){
                                            viewLayerTypes.add(ViewLayerType.META_MORPHEME);
                                        } else {
                                            if (viewLayerType.equalsIgnoreCase("pro")){
                                                viewLayerTypes.add(ViewLayerType.PROPBANK);
                                            } else {
                                                if (viewLayerType.equalsIgnoreCase("dep")){
                                                    viewLayerTypes.add(ViewLayerType.DEPENDENCY);
                                                } else {
                                                    if (viewLayerType.equalsIgnoreCase("sha") || viewLayerType.equalsIgnoreCase("chu")){
                                                        viewLayerTypes.add(ViewLayerType.SHALLOW_PARSE);
                                                    } else {
                                                        if (viewLayerType.equalsIgnoreCase("ese")){
                                                            viewLayerTypes.add(ViewLayerType.ENGLISH_SEMANTICS);
                                                        } else {
                                                            if (viewLayerType.equalsIgnoreCase("epr")){
                                                                viewLayerTypes.add(ViewLayerType.ENGLISH_PROPBANK);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        child = node.getFirstChild();
        while (child != null){
            if (child.getNodeName().equalsIgnoreCase("node") || child.getNodeName().equalsIgnoreCase("leaf"))
                children.add(new ParseNodeSearchable(this, child));
            child = child.getNextSibling();
        }
    }

    public SearchType getType(int index){
        return searchTypes.get(index);
    }

    public String getValue(int index){
        return searchValues.get(index);
    }

    public ViewLayerType getViewLayerType(int index){
        return viewLayerTypes.get(index);
    }

    public boolean isLeaf(){
        return isLeaf;
    }

    public int size(){
        return searchValues.size();
    }
}
