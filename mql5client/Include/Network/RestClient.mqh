#property strict

#include <ApplicationContext\ApplicationContext.mqh>

#include <Common\Structures.mqh>
#include <Network\RequestFactory.mqh>

//+------------------------------------------------------------------+
//| HTTP Rest client                                                 |
//+------------------------------------------------------------------+
class RestClient
  {
   string            url_formatter;
   RestConfig        rest_config;
   RequestFactory *  request_factory;
public:
                     RestClient()
     {
      rest_config = { "http://127.0.0.1", 80, "Content-Type: application/json\r\n", 3000 };;
      request_factory = new RequestFactory();
      url_formatter = "%s:%.0f%s";
     }
                    ~RestClient() { delete request_factory; }
public:
   string            Connect(string inputs);
   string            GetSignals(string advisor_id, string strategy_name);
private:
   void              Post(HttpRequest &request, HttpResponse &response);
   void              Get(HttpRequest &request, HttpResponse &response);
  };

//+------------------------------------------------------------------+
//| Bootstrap advisor register. Return advisor's UUID                |
//+------------------------------------------------------------------+
string::RestClient               Connect(string inputs)
  {
   HttpRequest request;
   request.url = StringFormat(url_formatter, rest_config.host, rest_config.port, "/api/advisor/add");
   request.body = request_factory.GetAddAdvisorRequestBody(inputs);
   request.headers = rest_config.headers;
   HttpResponse response;
   Post(request, response);
   if(response.status != 200)
     {
      Alert(StringFormat("Connection to Advisor: %.0f failed", Magic()));
      ExpertRemove();
     }
   else
      Alert(StringFormat("Connection to Advisor: %.0f success", Magic()));
   return response.body;
  }
//+------------------------------------------------------------------+
//| Get trade signal request                                         |
//+------------------------------------------------------------------+
string::RestClient               GetSignals(string advisor_id, string strategy_name)
  {
   HttpRequest request;
   request.url = StringFormat(url_formatter, rest_config.host, rest_config.port, "/api/signal");
   request.body = request_factory.GetSignalRequestBody(advisor_id, strategy_name);
   request.headers = rest_config.headers;
   HttpResponse response;
   Get(request, response);
   return response.body;
  }
//+------------------------------------------------------------------+
//| GET request low level defenition                                 |
//+------------------------------------------------------------------+
void::RestClient                   Get(HttpRequest &request, HttpResponse &response)
  {
   char responseBody[];
   uchar requestBody[];
   string responseHeaders;
   StringToCharArray(request.body, requestBody, 0, StringLen(request.body));
   int status = WebRequest("POST", request.url, request.headers, rest_config.timeout, requestBody, responseBody, responseHeaders);
   response.body = CharArrayToString(responseBody, 0, WHOLE_ARRAY, CP_UTF8);
   response.status = status;
  }
//+------------------------------------------------------------------+
//| POST request low level defenition                                |
//+------------------------------------------------------------------+
void::RestClient                   Post(HttpRequest &request, HttpResponse &response)
  {
   char responseBody[];
   uchar requestBody[];
   string responseHeaders;
   StringToCharArray(request.body, requestBody, 0, StringLen(request.body));
   int status = WebRequest("POST", request.url, request.headers, rest_config.timeout, requestBody, responseBody, responseHeaders);
   response.body = CharArrayToString(responseBody, 0, WHOLE_ARRAY, CP_UTF8);
   response.status = status;
  }
//+------------------------------------------------------------------+