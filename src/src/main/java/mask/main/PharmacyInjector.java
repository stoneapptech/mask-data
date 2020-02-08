package mask.main;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PharmacyInjector {
    public static void add(File dataDir, String code, String name, String address, String tel) throws ParseErrorException {
    /*public static void main(String[] args) throws ParseErrorException {
        String address = "彰市興北里三民路２０９號";
        String code = "5937010159";
        String name = "彰化明德藥局";
        String tel = "(04)7299085";*/

        String ID = "";
        String revStr= "{\"臺北\": {\"中正\": \"A.01\", \"大同\": \"A.02\", \"中山\": \"A.03\", \"松山\": \"A.04\", \"大安\": \"A.05\", \"萬華\": \"A.06\", \"信義\": \"A.07\", \"士林\": \"A.08\", \"北投\": \"A.09\", \"內湖\": \"A.10\", \"南港\": \"A.11\", \"文山\": \"A.12\"}, \"臺中\": {\"中\": \"B.01\", \"東\": \"B.02\", \"南\": \"B.03\", \"西\": \"B.04\", \"北\": \"B.05\", \"北屯\": \"B.06\", \"西屯\": \"B.07\", \"南屯\": \"B.08\", \"太平\": \"B.09\", \"大里\": \"B.10\", \"霧峰\": \"B.11\", \"烏日\": \"B.12\", \"豐原\": \"B.13\", \"后里\": \"B.14\", \"石岡\": \"B.15\", \"東勢\": \"B.16\", \"和平\": \"B.17\", \"新社\": \"B.18\", \"潭子\": \"B.19\", \"大雅\": \"B.20\", \"神岡\": \"B.21\", \"大肚\": \"B.22\", \"沙鹿\": \"B.23\", \"龍井\": \"B.24\", \"梧棲\": \"B.25\", \"清水\": \"B.26\", \"大甲\": \"B.27\", \"外埔\": \"B.28\", \"大安\": \"B.29\"}, \"基隆\": {\"仁愛\": \"C.01\", \"信義\": \"C.02\", \"中正\": \"C.03\", \"中山\": \"C.04\", \"安樂\": \"C.05\", \"暖暖\": \"C.06\", \"七堵\": \"C.07\"}, \"臺南\": {\"中西\": \"D.01\", \"東\": \"D.02\", \"南\": \"D.03\", \"北\": \"D.04\", \"安平\": \"D.05\", \"安南\": \"D.06\", \"永康\": \"D.07\", \"歸仁\": \"D.08\", \"新化\": \"D.09\", \"左\": \"D.10\", \"玉井\": \"D.11\", \"楠西\": \"D.12\", \"南化\": \"D.13\", \"仁德\": \"D.14\", \"關廟\": \"D.15\", \"龍崎\": \"D.16\", \"官田\": \"D.17\", \"麻豆\": \"D.18\", \"佳里\": \"D.19\", \"西港\": \"D.20\", \"七股\": \"D.21\", \"將軍\": \"D.22\", \"學甲\": \"D.23\", \"北門\": \"D.24\", \"新營\": \"D.25\", \"後壁\": \"D.26\", \"白河\": \"D.27\", \"東山\": \"D.28\", \"六甲\": \"D.29\", \"下營\": \"D.30\", \"柳營\": \"D.31\", \"鹽水\": \"D.32\", \"善化\": \"D.33\", \"大內\": \"D.34\", \"山上\": \"D.35\", \"新\": \"D.36\", \"安定\": \"D.37\"}, \"高雄\": {\"新興\": \"E.01\", \"前金\": \"E.02\", \"苓雅\": \"E.03\", \"鹽埕\": \"E.04\", \"鼓山\": \"E.05\", \"旗津\": \"E.06\", \"前\": \"E.07\", \"三民\": \"E.08\", \"楠梓\": \"E.09\", \"小港\": \"E.10\", \"左營\": \"E.11\", \"仁武\": \"E.12\", \"大社\": \"E.13\", \"東沙\": \"E.14\", \"南沙\": \"E.15\", \"岡山\": \"E.16\", \"路竹\": \"E.17\", \"阿蓮\": \"E.18\", \"田寮\": \"E.19\", \"燕巢\": \"E.20\", \"橋頭\": \"E.21\", \"梓官\": \"E.22\", \"彌陀\": \"E.23\", \"永安\": \"E.24\", \"湖內\": \"E.25\", \"鳳山\": \"E.26\", \"大寮\": \"E.27\", \"林園\": \"E.28\", \"鳥松\": \"E.29\", \"大樹\": \"E.30\", \"旗山\": \"E.31\", \"美濃\": \"E.32\", \"六龜\": \"E.33\", \"內門\": \"E.34\", \"杉林\": \"E.35\", \"甲仙\": \"E.36\", \"桃源\": \"E.37\", \"那瑪夏\": \"E.38\", \"茂林\": \"E.39\", \"茄萣\": \"E.40\"}, \"新北\": {\"萬里\": \"F.01\", \"金山\": \"F.02\", \"板橋\": \"F.03\", \"汐止\": \"F.04\", \"深坑\": \"F.05\", \"石碇\": \"F.06\", \"瑞芳\": \"F.07\", \"平溪\": \"F.08\", \"雙溪\": \"F.09\", \"貢寮\": \"F.10\", \"新店\": \"F.11\", \"坪林\": \"F.12\", \"烏來\": \"F.13\", \"永和\": \"F.14\", \"中和\": \"F.15\", \"土城\": \"F.16\", \"三峽\": \"F.17\", \"樹林\": \"F.18\", \"鶯歌\": \"F.19\", \"三重\": \"F.20\", \"新莊\": \"F.21\", \"泰山\": \"F.22\", \"林口\": \"F.23\", \"蘆洲\": \"F.24\", \"五股\": \"F.25\", \"八里\": \"F.26\", \"淡水\": \"F.27\", \"三芝\": \"F.28\", \"石門\": \"F.29\"}, \"宜蘭\": {\"宜蘭\": \"G.01\", \"頭城\": \"G.02\", \"礁溪\": \"G.03\", \"壯圍\": \"G.04\", \"員山\": \"G.05\", \"羅東\": \"G.06\", \"三星\": \"G.07\", \"大同\": \"G.08\", \"五結\": \"G.09\", \"冬山\": \"G.10\", \"蘇澳\": \"G.11\", \"南澳\": \"G.12\", \"釣魚臺\": \"G.13\"}, \"桃園\": {\"中壢\": \"H.01\", \"平\": \"H.02\", \"龍潭\": \"H.03\", \"楊梅\": \"H.04\", \"新屋\": \"H.05\", \"觀音\": \"H.06\", \"桃園\": \"H.07\", \"龜山\": \"H.08\", \"八德\": \"H.09\", \"大溪\": \"H.10\", \"復興\": \"H.11\", \"大園\": \"H.12\", \"蘆竹\": \"H.13\"}, \"新竹\": {\"東\": \"O.01\", \"北\": \"O.02\", \"香山\": \"O.03\",\"竹北\": \"J.01\", \"湖口\": \"J.02\", \"新豐\": \"J.03\", \"新埔\": \"J.04\", \"關西\": \"J.05\", \"芎林\": \"J.06\", \"寶山\": \"J.07\", \"竹東\": \"J.08\", \"五峰\": \"J.09\", \"橫山\": \"J.10\", \"尖石\": \"J.11\", \"北埔\": \"J.12\", \"峨眉\": \"J.13\"}, \"苗栗\": {\"竹南\": \"K.01\", \"頭份\": \"K.02\", \"三灣\": \"K.03\", \"南庄\": \"K.04\", \"獅潭\": \"K.05\", \"後龍\": \"K.06\", \"通霄\": \"K.07\", \"苑裡\": \"K.08\", \"苗栗\": \"K.09\", \"造橋\": \"K.10\", \"頭屋\": \"K.11\", \"公館\": \"K.12\", \"大湖\": \"K.13\", \"泰安\": \"K.14\", \"銅鑼\": \"K.15\", \"三義\": \"K.16\", \"西湖\": \"K.17\", \"卓蘭\": \"K.18\"}, \"南投\": {\"南投\": \"M.01\", \"中寮\": \"M.02\", \"草屯\": \"M.03\", \"國姓\": \"M.04\", \"埔里\": \"M.05\", \"仁愛\": \"M.06\", \"名間\": \"M.07\", \"集集\": \"M.08\", \"水里\": \"M.09\", \"魚池\": \"M.10\", \"信義\": \"M.11\", \"竹山\": \"M.12\", \"鹿谷\": \"M.13\"}, \"彰化\": {\"彰化\": \"N.01\", \"芬園\": \"N.02\", \"花壇\": \"N.03\", \"秀水\": \"N.04\", \"鹿港\": \"N.05\", \"福興\": \"N.06\", \"線西\": \"N.07\", \"和美\": \"N.08\", \"伸港\": \"N.09\", \"員林\": \"N.10\", \"社頭\": \"N.11\", \"永靖\": \"N.12\", \"埔心\": \"N.13\", \"溪湖\": \"N.14\", \"大村\": \"N.15\", \"埔鹽\": \"N.16\", \"田中\": \"N.17\", \"北斗\": \"N.18\", \"田尾\": \"N.19\", \"埤頭\": \"N.20\", \"溪州\": \"N.21\", \"竹塘\": \"N.22\", \"二林\": \"N.23\", \"大城\": \"N.24\", \"芳苑\": \"N.25\", \"二水\": \"N.26\"}, \"雲林\": {\"斗南\": \"P.01\", \"大埤\": \"P.02\", \"虎尾\": \"P.03\", \"土庫\": \"P.04\", \"褒忠\": \"P.05\", \"東勢\": \"P.06\", \"臺西\": \"P.07\", \"崙背\": \"P.08\", \"麥寮\": \"P.09\", \"斗六\": \"P.10\", \"林內\": \"P.11\", \"古坑\": \"P.12\", \"莿桐\": \"P.13\", \"西螺\": \"P.14\", \"二崙\": \"P.15\", \"北港\": \"P.16\", \"水林\": \"P.17\", \"口湖\": \"P.18\", \"四湖\": \"P.19\", \"元長\": \"P.20\"}, \"嘉義\": {\"東\": \"I.01\", \"西\": \"I.02\",\"番路\": \"Q.01\", \"梅山\": \"Q.02\", \"竹崎\": \"Q.03\", \"阿里山\": \"Q.04\", \"中埔\": \"Q.05\", \"大埔\": \"Q.06\", \"水上\": \"Q.07\", \"鹿草\": \"Q.08\", \"太保\": \"Q.09\", \"朴子\": \"Q.10\", \"東石\": \"Q.11\", \"六腳\": \"Q.12\", \"新港\": \"Q.13\", \"民雄\": \"Q.14\", \"大林\": \"Q.15\", \"溪口\": \"Q.16\", \"義竹\": \"Q.17\", \"布袋\": \"Q.18\"}, \"屏東\": {\"屏東\": \"T.01\", \"三地門\": \"T.02\", \"霧臺\": \"T.03\", \"瑪家\": \"T.04\", \"九如\": \"T.05\", \"里港\": \"T.06\", \"高樹\": \"T.07\", \"鹽埔\": \"T.08\", \"長治\": \"T.09\", \"麟洛\": \"T.10\", \"竹田\": \"T.11\", \"內埔\": \"T.12\", \"萬丹\": \"T.13\", \"潮州\": \"T.14\", \"泰武\": \"T.15\", \"來義\": \"T.16\", \"萬巒\": \"T.17\", \"崁頂\": \"T.18\", \"新埤\": \"T.19\", \"南州\": \"T.20\", \"林邊\": \"T.21\", \"東港\": \"T.22\", \"琉球\": \"T.23\", \"佳冬\": \"T.24\", \"新園\": \"T.25\", \"枋寮\": \"T.26\", \"枋山\": \"T.27\", \"春日\": \"T.28\", \"獅子\": \"T.29\", \"車城\": \"T.30\", \"牡丹\": \"T.31\", \"恆春\": \"T.32\", \"滿州\": \"T.33\"}, \"花蓮\": {\"花蓮\": \"U.01\", \"新城\": \"U.02\", \"秀林\": \"U.03\", \"吉安\": \"U.04\", \"壽豐\": \"U.05\", \"鳳林\": \"U.06\", \"光復\": \"U.07\", \"豐濱\": \"U.08\", \"瑞穗\": \"U.09\", \"萬榮\": \"U.10\", \"玉里\": \"U.11\", \"卓溪\": \"U.12\", \"富里\": \"U.13\"}, \"臺東\": {\"臺東\": \"V.01\", \"綠島\": \"V.02\", \"蘭嶼\": \"V.03\", \"延平\": \"V.04\", \"卑南\": \"V.05\", \"鹿野\": \"V.06\", \"關山\": \"V.07\", \"海端\": \"V.08\", \"池上\": \"V.09\", \"東河\": \"V.10\", \"成功\": \"V.11\", \"長濱\": \"V.12\", \"太麻里\": \"V.13\", \"金峰\": \"V.14\", \"大武\": \"V.15\", \"達仁\": \"V.16\"}, \"金門\": {\"金沙\": \"W.01\", \"金湖\": \"W.02\", \"金寧\": \"W.03\", \"金城\": \"W.04\", \"烈嶼\": \"W.05\", \"烏坵\": \"W.06\"}, \"澎湖\": {\"馬公\": \"X.01\", \"西嶼\": \"X.02\", \"望安\": \"X.03\", \"七美\": \"X.04\", \"白沙\": \"X.05\", \"湖西\": \"X.06\"}, \"連江\": {\"南竿\": \"Z.01\", \"北竿\": \"Z.02\", \"莒光\": \"Z.03\", \"東引\": \"Z.04\"}}";
        JSONObject revID = new JSONObject(revStr);
        List<String> addrSplit;

        address = address.replaceAll("台", "臺").replaceAll("巿", "市");
        try {
            addrSplit = Arrays.asList(address.split("鄉|鎮|市|區|縣|群島"));
            //System.out.println(addrSplit.toString());

            if (addrSplit.size() >= 3) {
                try {
                    ID = (String) ((JSONObject) revID.get(addrSplit.get(0))).get(addrSplit.get(1));
                } catch (Exception e) {
                    //System.out.println("E: 3: \n\t"+address);
                }
            }

            else if (addrSplit.size() == 2) {
                try {
                    JSONObject city = (JSONObject) revID.get(addrSplit.get(0));
                    ID = (String) city.get(addrSplit.get(0));
                } catch (Exception e) {
                    //System.out.println("E: 2\n\t"+address);
                }
            }

        } catch (Exception e) {
            //System.out.println("E: Split Error\n\t"+address);
        }

        if(!ID.isEmpty()) {
            //System.out.println(ID);
            if(modify(dataDir, ID, name, address, tel, code)) {
                log("add " + code);
            }
            else {
                String str = "IOError:\n\t" +
                        code + ", \n\t" +
                        name + ", \n\t" +
                        tel + ", \n\t" + address;
                log(str);
            }
        }
        else {
            String str = "ParseError:\n\t" +
                    code + ", \n\t" +
                    name + ", \n\t" +
                    tel + ", \n\t" + address;
            log(str);
            throw new ParseErrorException();
        }
    }

    private static boolean modify(File dataDir, String areaID, String name, String address, String tel, String code) {
        String path = dataDir.getPath() + "/";
        tel = tel.replaceAll("\\(", "").replaceAll("\\)", "-");

        // Read
        StringBuilder areaStr = new StringBuilder();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path + "area/" + areaID + ".json"))) {
            String line = bufferedReader.readLine();
            while(line != null) {
                areaStr.append(line);
                line = bufferedReader.readLine();
            }

            // Generate
            JSONObject area = new JSONObject(areaStr.toString());
            //System.out.println(area);
            String city = (String) area.get("city");
            String town = (String) area.get("town");
            JSONArray stores = (JSONArray) area.get("store");

            for(int i = stores.length()-1; i >= 0; i--) {
                JSONObject s = (JSONObject) stores.get(i);
                if(code.equals(s.get("code"))) {
                    stores.remove(i);
                    log("remove duplicated " + code);
                }
            }

            JSONObject storeArea = new JSONObject();
            storeArea.put("name", name);
            storeArea.put("address", address);
            storeArea.put("tel", tel);
            storeArea.put("code", code);

            stores.put(storeArea);

            JSONObject storeCode = new JSONObject();
            storeCode.put("area_id", areaID);
            storeCode.put("city", city);
            storeCode.put("town", town);
            storeCode.put("name", name);
            storeCode.put("address", address);
            storeCode.put("tel", tel);

            // Write
            try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path + "area/" + areaID + ".json"))) {
                String fileContent = area.toString();
                bufferedWriter.write(fileContent);
            } catch (IOException e) {
                return false;
            }

            try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path + "store/" + code + ".json"))) {
                String fileContent = storeCode.toString();
                bufferedWriter.write(fileContent);
                return true;
            } catch (IOException e) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    private static void log(String message) {
        File file = new File("log.txt");
        try {
            FileWriter fr = new FileWriter(file, true);
            BufferedWriter br = new BufferedWriter(fr);
            br.write(new Date().toString() + ": " + message + "\n");

            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
