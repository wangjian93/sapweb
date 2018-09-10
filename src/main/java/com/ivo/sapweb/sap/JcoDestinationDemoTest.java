package com.ivo.sapweb.sap;

import com.alibaba.druid.support.json.JSONUtils;
import com.ivo.sapweb.sap.core.BapiCaller;
import com.ivo.sapweb.sap.core.SapConnectionPool;
import com.sap.conn.jco.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * demo
 *
 * @author wangjian
 * @date 2018/8/12
 */
@Controller
public class JcoDestinationDemoTest {

    @Autowired
    private BapiCaller bapiCaller;

    @RequestMapping("/jco3")
    @ResponseBody
    public Map test3() {

        //String functionName = "Z_BOM_LIST_EDW";
        //String[] outTables = {"TEMPSTKO", "TEMPSTPO", "TEMPMAST", "TEMPMKAL"};

//        String functionName = "ZSD_EIF_VBSN";

//        Map<String, String> importParams = new HashMap<>(3);
//        importParams.put("VBELN", "0080010000");
//        importParams.put("POSNR", "10");
//        importParams.put("AESKD", "M10-Z");

//        String[] L_SUBRC = {"L_SUBRC"};

        String functionName = "ZM1LINESTORE_TO_WO";
        Map importParams = new HashMap();
        importParams.put("FLAG", "2");
        importParams.put("P_WERKS", "3000");

        String[] tablesName = {"I_LINESTORE_TO_WO"};

        Map map = bapiCaller.sapCall(functionName, null, null, null,
                null, null, tablesName);

        return map;
    }

    @RequestMapping("/jco")
    @ResponseBody
    public Map test2() {
        String functionName = "ZSD_EIF_VBSN";
        Map<String, String> importParams = new HashMap<>(3);
        importParams.put("VBELN", "0080010000");
        importParams.put("POSNR", "10");
        importParams.put("AESKD", "M10-Z");

        String[] exportParams = {"L_SUBRC"};
        String[] outStructures = {"RETURN"};
        String[] outTables = {"RETURN"};

        Map map = new HashMap<>();

        map = bapiCaller.sapCall(functionName, importParams, null, null, exportParams, null,
                    null);


        return map;
    }

    @Test
    public void test() throws JCoException {

//        String functionName = "ZMM_MTRL_PRICE";
//        String functionName = "ZALL_BATCH";
        String functionName = "Z_BOM_LIST_EDW";
//        String functionName = "ZM1LINESTORE_TO_WO";

        //获取连接
        JCoDestination jCoDestination = SapConnectionPool.getSAPDestination();

        System.out.println("Attributes:");
        System.out.println(jCoDestination.getAttributes());

        JCoFunctionTemplate jCoFunctionTemplate = jCoDestination.getRepository().getFunctionTemplate(functionName);
        if(jCoFunctionTemplate == null) {
            throw new RuntimeException("cannot run without " + functionName + " functions");
        }

        System.out.println("************Import*************");
        JCoListMetaData jCoListMetaData = jCoFunctionTemplate.getImportParameterList();
        if(jCoListMetaData != null ) {
            descriptin(jCoListMetaData);
        }
        System.out.println("************Import*************");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");

        System.out.println("************Export*************");
        JCoListMetaData jCoListMetaData1 = jCoFunctionTemplate.getExportParameterList();
        if(jCoListMetaData1 != null ) {
            descriptin(jCoListMetaData1);
        }
        System.out.println("************Export*************");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");

        System.out.println("************Table*************");
        JCoListMetaData jCoListMetaData2 = jCoFunctionTemplate.getTableParameterList();
        if(jCoListMetaData2 != null ) {
            descriptin(jCoListMetaData2);
        }
        System.out.println("************Table*************");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");

        System.out.println("************Changing*************");
        JCoListMetaData jCoListMetaData3 = jCoFunctionTemplate.getChangingParameterList();
        if(jCoListMetaData3 != null ) {
            descriptin(jCoListMetaData3);
        }
        System.out.println("************Changing*************");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");


        System.out.println("************FunctionInterface*************");
        JCoListMetaData jCoListMetaData4 = jCoFunctionTemplate.getFunctionInterface();
        if(jCoListMetaData4 != null ) {
            descriptin(jCoListMetaData4);
        }
        System.out.println("************FunctionInterface*************");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");

    }

    public void descriptin(JCoListMetaData jCoListMetaData) {
        int fielCount = jCoListMetaData.getFieldCount();
        for(int i=0; i<fielCount; i++) {
            System.out.println("#######################################################");

//            String name = jCoListMetaData.getName();
//            System.out.println("name: " + name);

            String fielName = jCoListMetaData.getName(i);
            System.out.println("fileName: " + fielName);

            String q = jCoListMetaData.getDefault(i);
            System.out.println("default: " + q);


            int indexOf = jCoListMetaData.indexOf(fielName);
            System.out.println("indexOf: " + indexOf);

            boolean hasField = jCoListMetaData.hasField(fielName);
            System.out.println("hasField: " + hasField);

            /***/
            String recordTypeName = jCoListMetaData.getRecordTypeName(i);
            String recordTypeName1 = jCoListMetaData.getRecordTypeName(fielName);
            System.out.println("recordTypeName: " + recordTypeName);

            JCoRecordMetaData jCoRecordMetaData = jCoListMetaData.getRecordMetaData(i);
            if(jCoRecordMetaData != null) {
                int count = jCoRecordMetaData.getFieldCount();
                System.out.println("jCoRecordMetaData FieldCount: " + count);
                for(int j=0; j<count; j++) {
                    String name = jCoRecordMetaData.getName(j);
                    int legnth = jCoRecordMetaData.getLength(j);
                    String ds = jCoRecordMetaData.getDescription(j);
                    int d = jCoRecordMetaData.getDecimals(j);
                    String t = jCoRecordMetaData.getTypeAsString(j);
                    String ty = jCoRecordMetaData.getClassNameOfField(i);

                    System.out.print("         ");
                    System.out.println(name + "        " + legnth + "        " + d + "          " +
                            t + "            " + ds + "             " + ty);
                }


            }
            else {
                System.out.println("jCoRecordMetaData FieldCount: " + jCoRecordMetaData);
            }

            /***/
            JCoExtendedFieldMetaData jCoExtendedFieldMetaData = jCoListMetaData.getExtendedFieldMetaData(i);
            JCoExtendedFieldMetaData jCoExtendedFieldMetaData1 = jCoListMetaData.getExtendedFieldMetaData(fielName);
            System.out.println("jCoExtendedFieldMetaData: " + jCoExtendedFieldMetaData);


            int unicodeByteLength = jCoListMetaData.getUnicodeByteLength(i);
            int unicodeByteLength1 = jCoListMetaData.getUnicodeByteLength(fielName);
            System.out.println("unicodeByteLength: " + unicodeByteLength);

            int byteLength = jCoListMetaData.getByteLength(i);
            int byteLength1 = jCoListMetaData.getByteLength(fielName);
            System.out.println("byteLength: " + byteLength);

            int type = jCoListMetaData.getType(i);
            int type1 = jCoListMetaData.getType(fielName);
            System.out.println("type: " + type);

            String typeName = jCoListMetaData.getTypeAsString(i);
            String typeName1 = jCoListMetaData.getTypeAsString(fielName);
            System.out.println("typeName: " + typeName);

            String classNameOfField = jCoListMetaData.getClassNameOfField(i);
            String classNameOfField1 = jCoListMetaData.getClassNameOfField(fielName);
            System.out.println("classNameOfField: " + classNameOfField);

            int decimals = jCoListMetaData.getDecimals(i);
            int decimals1 = jCoListMetaData.getDecimals(fielName);
            System.out.println("decimals: " + decimals);

            String description = jCoListMetaData.getDescription(i);
            String description1 = jCoListMetaData.getDescription(fielName);
            System.out.println("description: " + description);

            boolean isStructure = jCoListMetaData.isStructure(i);
            boolean isStructure1 = jCoListMetaData.isStructure(fielName);
            System.out.println("isStructure: " + isStructure);

            /***/
            boolean isNestedType1Structure = jCoListMetaData.isNestedType1Structure(i);
            boolean isNestedType1Structure1 = jCoListMetaData.isNestedType1Structure(fielName);
            System.out.println("isNestedType1Structure: " + isNestedType1Structure);


            boolean isTable = jCoListMetaData.isTable(i);
            boolean isTable1 = jCoListMetaData.isTable(fielName);
            System.out.println("isTable: " + isTable);

            boolean isAbapObject = jCoListMetaData.isAbapObject(i);
            boolean isAbapObject1 = jCoListMetaData.isAbapObject(fielName);
            System.out.println("isAbapObject: " + isAbapObject);

            /****/
            String recordFieldNam = jCoListMetaData.getRecordFieldName(i);
            String recordFieldNam1 = jCoListMetaData.getRecordFieldName(fielName);
            System.out.println("recordFieldNam: " + recordFieldNam);

            boolean isOptional = jCoListMetaData.isOptional(i);
            boolean isOptional1 = jCoListMetaData.isOptional(fielName);
            System.out.println("isOptional: " + isOptional);

            boolean isImport = jCoListMetaData.isImport(i);
            boolean isImport1 = jCoListMetaData.isImport(fielName);
            System.out.println("isImport: " + isImport);

            boolean isExport = jCoListMetaData.isExport(i);
            boolean isExport1 = jCoListMetaData.isExport(fielName);
            System.out.println("isExport: " + isExport);

            boolean isChanging = jCoListMetaData.isChanging(i);
            boolean isChanging1 = jCoListMetaData.isChanging(fielName);
            System.out.println("isChanging: " + isChanging);

            JCoTable jCoTable;



            //void setName(String var1);
            //void lock();
            //boolean isLocked();

            //void add(String var1, int var2, int var3, int var4, int var5);

            //void add(String var1, int var2, JCoRecordMetaData var3, int var4);

            //void add(String var1, int var2, int var3, int var4, int var5, String var6, String var7, int var8, Object var9, JCoExtendedFieldMetaData var10);

            //void add(String var1, int var2, int var3, int var4, int var5, String var6, String var7, int var8, Object var9, String var10, JCoExtendedFieldMetaData var11);

            //boolean isException(int var1);

            //boolean isException(String var1);

            //String getDefault(int var1);

            //String getDefault(String var1);
        }
    }
}
