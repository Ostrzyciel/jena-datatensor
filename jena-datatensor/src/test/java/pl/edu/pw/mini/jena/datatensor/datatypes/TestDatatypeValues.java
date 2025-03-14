package pl.edu.pw.mini.jena.datatensor.datatypes;

import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.junit.Assert;
import org.junit.Test;
import pl.edu.pw.mini.jena.datatensor.datatypes.implementations.BooleanDataTensor;
import pl.edu.pw.mini.jena.datatensor.datatypes.implementations.NumericDataTensor;

public class TestDatatypeValues {

    @Test
    public void numericCompareTests() {
        numericCompareTest(true, "{\"type\":\"int32\",\"shape\":[1,2,2],\"data\":[3,2,3,4]}", "{\"type\":\"int32\",\"shape\":[1,2,2],\"data\":[3,2,3,4]}");
        numericCompareTest(false, "{\"type\":\"int32\",\"shape\":[1,2,2],\"data\":[3,2,3,4]}", "{\"type\":\"int32\",\"shape\":[1,2,2],\"data\":[3,2,3,5]}");
        numericCompareTest(false, "{\"type\":\"int32\",\"shape\":[1,2,2],\"data\":[3,2,3,4]}", "{\"type\":\"int32\",\"shape\":[1,1,3],\"data\":[3,2,3]}");
        numericCompareTest(false, "{\"type\":\"int32\",\"shape\":[1,2,2],\"data\":[3,2,3,4]}", "{\"type\":\"int32\",\"shape\":[1,5],\"data\":[3,2,3,4,5]}");
        numericCompareTest(false, "{\"type\":\"int32\",\"shape\":[1,2,2],\"data\":[3,2,3,4]}", "{\"type\":\"int32\",\"shape\":[2,1,2],\"data\":[3,2,3,4]}");
    }

    @Test
    public void booleanCompareTests() {
        booleanCompareTest(true, "{\"shape\":[1,2,2],\"data\":[true,false,true,false]}", "{\"shape\":[1,2,2],\"data\":[true,false,true,false]}");
        booleanCompareTest(false, "{\"shape\":[1,2,2],\"data\":[true,false,true,false]}", "{\"shape\":[1,2,2],\"data\":[true,false,true,true]}");
        booleanCompareTest(false, "{\"shape\":[1,2,2],\"data\":[true,false,true,false]}", "{\"shape\":[1,3],\"data\":[true,false,true]}");
        booleanCompareTest(false, "{\"shape\":[1,2,2],\"data\":[true,false,true,false]}", "{\"shape\":[1,5],\"data\":[true,false,true,false,true]}");
        booleanCompareTest(false, "{\"shape\":[1,2,2],\"data\":[true,false,true,false]}", "{\"shape\":[2,1,2],\"data\":[true,false,true,false]}");
    }


    private static void numericCompareTest(Boolean expected, String lex1, String lex2) {
        Node d1 = NodeFactory.createLiteral(lex1, null, NumericDataTensor.INSTANCE);
        Node d2 = NodeFactory.createLiteral(lex2, null, NumericDataTensor.INSTANCE);
        Assert.assertEquals(expected, NumericDataTensor.INSTANCE.isEqual(d1.getLiteral(), d2.getLiteral()));
    }


    private void booleanCompareTest(Boolean expected, String lex1, String lex2) {
        Node d1 = NodeFactory.createLiteral(lex1, null, BooleanDataTensor.INSTANCE);
        Node d2 = NodeFactory.createLiteral(lex2, null, BooleanDataTensor.INSTANCE);
        Assert.assertEquals(expected, BooleanDataTensor.INSTANCE.isEqual(d1.getLiteral(), d2.getLiteral()));
    }
}
