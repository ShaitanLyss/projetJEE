//package fr.cyu.airportmadness.vie.pebble;
//
////import io.pebbletemplates.pebble.attributes.AttributeResolver;
////import io.pebbletemplates.pebble.extension.*;
////import io.pebbletemplates.pebble.operator.BinaryOperator;
////import io.pebbletemplates.pebble.operator.UnaryOperator;
////import io.pebbletemplates.pebble.tokenParser.TokenParser;
//import org.slf4j.LoggerFactory;
//import org.yaml.snakeyaml.Yaml;
//import org.slf4j.Logger;
//
//import java.io.InputStream;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class GlobalsExtension implements Extension {
//    private final Logger logger = LoggerFactory.getLogger(GlobalsExtension.class);
//
//
//
//    @Override
//    public Map<String, Filter> getFilters() {
//        return null;
//    }
//
//    @Override
//    public Map<String, Test> getTests() {
//        return null;
//    }
//
//    @Override
//    public Map<String, Function> getFunctions() {
//        return null;
//    }
//
//    @Override
//    public List<TokenParser> getTokenParsers() {
//        return null;
//    }
//
//    @Override
//    public List<BinaryOperator> getBinaryOperators() {
//        return null;
//    }
//
//    @Override
//    public List<UnaryOperator> getUnaryOperators() {
//        return null;
//    }
//
//    @Override
//    public Map<String, Object> getGlobalVariables() {
//        Map<String, Object> res;
//        Yaml yaml = new Yaml();
//
//        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("config/pebble.yaml")) {
//             res = yaml.load(inputStream);
//
//        } catch (Exception  e) {
//            logger.error("Échec de la lecture du fichier de configuration de pebble : " +e.getMessage());
//            res = new HashMap<>();
//        }
//
//        return res;
//    }
//
//    @Override
//    public List<NodeVisitorFactory> getNodeVisitors() {
//        return null;
//    }
//
//    @Override
//    public List<AttributeResolver> getAttributeResolver() {
//        return null;
//    }
//}
