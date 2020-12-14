package com.ts.uhf;import com.ts.animal.service.AccessRecordService;import com.ts.common.config.TsConfig;import com.ts.system.base.BaseInterface;import com.ts.uhf.util.Conversion;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Service;import uhf.MultiLableCallBack;import uhf.Reader;import uhf.Types;import java.util.concurrent.ArrayBlockingQueue;import java.util.concurrent.BlockingQueue;@Servicepublic class UhfReader implements MultiLableCallBack, BaseInterface {    static UhfReader uhfReader;    static Reader readerControllor;    static String flag = "0";    static String port;    static BlockingQueue<String> blockingQueue;    @Autowired    private AccessRecordService accessRecordService;    public static void main(String[] args) {        uhfReader = new UhfReader();        uhfReader.init();    }    @Override    public void method(String data) {        String[] result = (data + "," + flag).split("\\,");        byte type = Conversion.toBytes(result[1])[0];        switch (type) {            case Types.START_MULTI_EPC_RESPOND:            case Types.START_SINGLE_EPC_RESPOND:                String ECP = result[5].replace("-", "");                //System.out.println("读取到数据:"+ ECP);                UhfReader.put(ECP);                break;            default:                break;        }    }    @Override    public void init() {        uhfReader = new UhfReader();        readerControllor = new Reader(uhfReader);        blockingQueue = new ArrayBlockingQueue<String>(1000, true);        port = TsConfig.getProperty("ts.terminal.accessDevice.port");        readerControllor.ServerStart("0.0.0.0", Integer.valueOf(port));        new Thread(new Runnable() {            @Override            public void run() {                try {                    Thread.sleep(5000);                    readerControllor.StartMultiEPC();//控制所有设备                } catch (Exception e) {                }            }        }).start();        new Thread(new Runnable() {            @Override            public void run() {                while (true) {                    //入库                    try {                        accessRecordService.saveBySystem(blockingQueue.take());                    } catch (Exception e) {                        e.printStackTrace();                    }                    //end 入库                }            }        }).start();    }    public static void put(String num) {        try {            blockingQueue.put(num);        } catch (InterruptedException e) {            e.printStackTrace();        }    }    public static UhfReader getUhfReader() {        return uhfReader;    }    public static Reader getReaderControllor() {        return readerControllor;    }}