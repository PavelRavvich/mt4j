#property strict

#include <Library\ApplicationContext\ApplicationContext.mqh>

//+------------------------------------------------------------------+
//| HTTP Rest client                                                 |
//+------------------------------------------------------------------+
class CRestClient
  {
private:
   RestConfig              rest_config;
   string                  url_formatter;
   CRequestFactory *       request_factory;
   CExceptionInterceptor * exception_interceptor;
public:
                     CRestClient(void);
                    ~CRestClient(void);
public:
   string            Connect(const string inputs);
   string            GetSignals(const string advisor_id, const string strategy_name);
private:
   void              Post(HttpRequest &request, HttpResponse &response);
   void              Get(HttpRequest &request, HttpResponse &response);
  };

//+------------------------------------------------------------------+
//| Default constructor                                              |
//+------------------------------------------------------------------+
CRestClient::CRestClient(void)
  {
   url_formatter = "%s:%.0f%s";
   rest_config.host = Host();
   rest_config.port = Port();
   rest_config.timeout = Timeout();
   request_factory = RequestFactory();
   exception_interceptor = ExceptionInterceptor();
   rest_config.headers = "Content-Type: application/json\r\n";
  }
//+------------------------------------------------------------------+
//| Destructor                                                       |
//+------------------------------------------------------------------+
CRestClient::~CRestClient(void) {}
//+------------------------------------------------------------------+
//| Bootstrap advisor register. Return advisor's UUID                |
//+------------------------------------------------------------------+
string CRestClient::Connect(const string inputs)
  {
   HttpRequest request;
   request.url = StringFormat(url_formatter, rest_config.host, rest_config.port, "/api/advisor/add");
   request.body = request_factory.GetAddAdvisorRequestBody(inputs);
   request.headers = rest_config.headers;
   HttpResponse response;
   Post(request, response);
   return response.body;
  }
//+------------------------------------------------------------------+
//| Get trade signal request                                         |
//+------------------------------------------------------------------+
string CRestClient::GetSignals(string advisor_id, const string strategy_name)
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
void CRestClient::Get(HttpRequest &request, HttpResponse &response)
  {
   char responseBody[];
   uchar requestBody[];
   string responseHeaders;
   StringToCharArray(request.body, requestBody, 0, StringLen(request.body));
   int status = WebRequest("GET", request.url, request.headers, rest_config.timeout, requestBody, responseBody, responseHeaders);
   response.body = CharArrayToString(responseBody, 0, WHOLE_ARRAY, CP_UTF8);
   response.status = status;
   exception_interceptor.Intercept(response);
  }
//+------------------------------------------------------------------+
//| POST request low level defenition                                |
//+------------------------------------------------------------------+
void CRestClient::Post(HttpRequest &request, HttpResponse &response)
  {
   char responseBody[];
   uchar requestBody[];
   string responseHeaders;
   StringToCharArray(request.body, requestBody, 0, StringLen(request.body));
   int status = WebRequest("POST", request.url, request.headers, rest_config.timeout, requestBody, responseBody, responseHeaders);
   response.body = CharArrayToString(responseBody, 0, WHOLE_ARRAY, CP_UTF8);
   response.status = status;
   exception_interceptor.Intercept(response);
  }
//+------------------------------------------------------------------+