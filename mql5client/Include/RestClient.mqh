#property copyright "Copyright © 2006-2017"
#property version "1.12"
#property strict

#include <RequestFactory.mqh>

struct HttpResponce
  {
   int               status;
   string            body;
  };

struct HttpRequest
  {
   string            url;
   string            body;
   string            headers;
  };

struct RestConfig
  {
   string            host;
   long              magic;
   string            inputs;
   int               buffer_size;
  };


class RestClient
  {

   long              _magic;

   string            _inputs;

   string            _host;

   RequestFactory *  _requestFactory;

public:
                     RestClient(RestConfig & restConfig)
     {
      _host = restConfig.host;
      _magic = restConfig.magic;
      _inputs = restConfig.inputs;
      _requestFactory = new RequestFactory(restConfig.buffer_size);
     }
                    ~RestClient() { delete _requestFactory; }

public:

   string               Connect()
     {
      return "Advisor-UUID-....";
     }

private:

   void                   Post(HttpRequest &request, HttpResponce &responce) {}

   void                   Get(HttpRequest &request, HttpResponce &responce) {}

  };