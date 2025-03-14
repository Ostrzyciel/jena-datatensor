package pl.edu.pw.mini.jena.datatensor.datatypes.utils.mapper;

import org.nd4j.linalg.api.buffer.DataType;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import pl.edu.pw.mini.jena.datatensor.datatypes.utils.jackson.*;

public class NumericMapper {

    public static JSONData mapINDArrayToJSONData(INDArray array) {
        JSONData jsonData;
        try {
            String type = array.data().dataType().toString().toLowerCase();
            switch (type) {
                case "double":
                case "float64":
                    jsonData = new Float64JSONData();
                    ((Float64JSONData) jsonData).setData(array.data().asDouble());
                    break;
                case "float32":
                case "float":
                    jsonData = new Float32JSONData();
                    ((Float32JSONData) jsonData).setData(array.data().asFloat());
                    break;
                case "half":
                case "float16":
                    jsonData = new Float16JSONData();
                    ((Float16JSONData) jsonData).setData(array.data().asFloat());
                    break;
                case "short":
                case "int16":
                    jsonData = new Int16JSONData();
                    short[] data = getShortArray(array);
                    ((Int16JSONData) jsonData).setData(data);
                    break;
                case "int":
                case "int32":
                    jsonData = new Int32JSONData();
                    ((Int32JSONData) jsonData).setData(array.data().asInt());
                    break;
                case "long":
                case "int64":
                    jsonData = new Int64JSONData();
                    ((Int64JSONData) jsonData).setData(array.data().asLong());
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported data type: " + type);
            }
            jsonData.setShape(array.shape());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error parsing tensor: " + e.getMessage());
        }
        return jsonData;
    }

    private static short[] getShortArray(INDArray array) {
        short[] data = new short[(int) array.length()];
        for (int i = 0; i < array.length(); i++) data[i] = array.data().getNumber(i).shortValue();
        return data;
    }

    public static INDArray mapJSONDataToINDArray(JSONData jsonData) throws IllegalArgumentException {
        INDArray indArray;
        long[] shape = jsonData.getShape();

        switch (jsonData.getType()) {
            case "float16":
                try {
                    float[] data = ((Float16JSONData) jsonData).getData();
                    indArray = Nd4j.create(data, shape, DataType.FLOAT16);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Error parsing tensor: " + e.getMessage());
                }
                break;
            case "float32":
                try {
                    float[] data = ((Float32JSONData) jsonData).getData();
                    indArray = Nd4j.create(data, shape, DataType.FLOAT);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Error parsing tensor: " + e.getMessage());
                }
                break;
            case "float64":
                try {
                    double[] data = ((Float64JSONData) jsonData).getData();
                    indArray = Nd4j.create(data, shape, DataType.DOUBLE);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Error parsing tensor: " + e.getMessage());
                }
                break;
            case "int16":
                try {
                    short[] data = ((Int16JSONData) jsonData).getData();
                    indArray = Nd4j.create(data, shape, DataType.INT16);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Error parsing tensor: " + e.getMessage());
                }
                break;
            case "int32":
                try {
                    int[] data = ((Int32JSONData) jsonData).getData();
                    indArray = Nd4j.create(data, shape, DataType.INT32);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Error parsing tensor: " + e.getMessage());
                }
                break;
            case "int64":
                try {
                    long[] data = ((Int64JSONData) jsonData).getData();
                    indArray = Nd4j.create(data, shape, DataType.INT64);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Error parsing tensor: " + e.getMessage());
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported type: " + jsonData.getType());
        }

        if (indArray == null) {
            throw new IllegalArgumentException("Error parsing tensor");
        }
        return indArray;
    }
}
