namespace java com.imooc.thrift.message
namespace py message.api

service MessageService{
    boolean sendMobileMessage(1:string mobile, 2:string message);
    boolean sendEmailMessage(1:string email, 2:string message);
}