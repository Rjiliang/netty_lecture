package com.cherry.thrift;

import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.TTransportFactory;
import thrift.generated.PersonService;

public class ThriftServer {

    public static void main(String[] args) throws Exception {
        //非阻塞的socket
        TNonblockingServerSocket socket = new TNonblockingServerSocket(8899);
        THsHaServer.Args args1 = new THsHaServer.Args(socket).minWorkerThreads(2).maxWorkerThreads(4);
        PersonService.Processor<PersonServiceImpl> processor = new PersonService.Processor<>(new PersonServiceImpl());

        args1.protocolFactory(new TCompactProtocol.Factory());
        args1.transportFactory(new TFastFramedTransport.Factory());
        args1.processorFactory(new TProcessorFactory(processor));

        TServer server = new THsHaServer(args1);
        System.out.println("Thrift server started!");
        server.serve();//死循环，非阻塞

    }

}
