#property strict

#include <Library\ApplicationContext\ApplicationContext.mqh>

//+------------------------------------------------------------------+
//| Exceptions interceptor for all server exception logging.         |
//+------------------------------------------------------------------+
class CExceptionInterceptor
  {
private:
   CJAVal *          json_mapper;
public:
                     CExceptionInterceptor(void);
                    ~CExceptionInterceptor(void) {}
public:
   void CExceptionInterceptor::Intercept(HttpResponse &response);
  };

//+------------------------------------------------------------------+
//| Default constructor                                              |
//+------------------------------------------------------------------+
CExceptionInterceptor::CExceptionInterceptor(void) { json_mapper = JsonMapper(); }
//+------------------------------------------------------------------+
//| Deafault exception interceptor.                                  |
//+------------------------------------------------------------------+
void CExceptionInterceptor::Intercept(HttpResponse &response)
  {
   if(response.status != 400)
      return;

   json_mapper.Clear();
   json_mapper.Deserialize(response.body);
   string time = json_mapper["time"].ToStr();
   string message = json_mapper["message"].ToStr();
   bool isCritical = json_mapper["isCritical"].ToBool();

   if(isCritical)
     {
      Alert("Advisor stopped by error. ", time);
      Alert(message);
      ExpertRemove();
     }
   else
     {
      Alert(message, " ", time);
     }
  }
