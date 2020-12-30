#property strict

//+------------------------------------------------------------------+
//| Build JSON array from inputs.                                    |
//+------------------------------------------------------------------+
class CInputsProvider
  {
private:
   string            inputs;
   bool              is_builed;
   string            empty_inputs;
   string            input_long_formatter;
   string            input_string_formatter;
   string            input_double_formatter;
   string            input_boolean_formatter;
   string            keys[];
public:
                     CInputsProvider(void);
                    ~CInputsProvider(void);
public:
   string            Build(void);
   string            GetJsonInputs(void);
   void              InputBoolean(const string key, const bool value);
   void              InputString(const string key, const string value);
   void              InputDouble(const string key, const double value);
   void              InputLong(const string key, const long value);
   void              InputInteger(const string key, const int value);
   void              InputDatetime(const string key, const datetime date);
   void              InputTimeSeconds(const string key, const int seconds);
   void              InputTimeMilliseconds(const string key, const long milliseconds);
   string            ToString(void);
private:
   void              AddKey(string key);
   bool              ContainKey(string key);
  };

//+------------------------------------------------------------------+
//| Default constructor                                              |
//+------------------------------------------------------------------+
CInputsProvider::CInputsProvider(void)
  {
   inputs = "";
   is_builed = false;
   empty_inputs = "[]";
   input_long_formatter = "{ \"key\": \"%s\", \"value\": %.0f, \"type\": \"NUMBER\" }";
   input_double_formatter = "{ \"key\": \"%s\", \"value\": %.5f, \"type\": \"NUMBER\" }";
   input_boolean_formatter = "{ \"key\": \"%s\", \"value\": %s, \"type\": \"BOOLEAN\" }";
   input_string_formatter = "{ \"key\": \"%s\", \"value\": \"%s\", \"type\": \"STRING\" }";
  }
//+------------------------------------------------------------------+
//| Destructor                                                       |
//+------------------------------------------------------------------+
CInputsProvider::~CInputsProvider(void) {}
//+------------------------------------------------------------------+
//| Build inputs as JSON array. Can be called only ones.             |
//+------------------------------------------------------------------+
string CInputsProvider::Build(void)
  {
   if(inputs == "")
     {
      Alert("Warning! Inputs is empty.");
      return empty_inputs;
     }

   if(is_builed)
      Alert("Error! Method Build() can be called only ones.");

   is_builed = true;
   return inputs = "[" + StringSubstr(inputs, 0, StringLen(inputs) - 2) + "]";
  }
//+------------------------------------------------------------------+
//| Get inputs as JSON array. Reqired call Build() before.           |
//+------------------------------------------------------------------+
string CInputsProvider::GetJsonInputs(void)
  {
   if(!is_builed)
     {
      Alert("Build() wasn't called. Method Build() should be called before GetInputs().");
      return empty_inputs;
     }
   return inputs;
  }
//+------------------------------------------------------------------+
//| Add boolean input                                                |
//+------------------------------------------------------------------+
void CInputsProvider::InputBoolean(const string key, const bool value)
  {
   if(is_builed)
     {
      Alert("Build() already done. Method Build() can be called only once.");
      return;
     }

   if(ContainKey(key))
     {
      Alert(StringFormat("Error add input property! Key: %s already exist.", key));
      return;
     }

   AddKey(key);
   inputs = inputs + StringFormat(input_boolean_formatter, key, value ? "true" : "false") + ", ";
  }
//+------------------------------------------------------------------+
//| Add string input                                                 |
//+------------------------------------------------------------------+
void CInputsProvider::InputString(const string key, const string value)
  {
   if(is_builed)
     {
      Alert("Build() already done. Method Build() can be called only once.");
      return;
     }

   if(ContainKey(key))
     {
      Alert(StringFormat("Error add input property! Key: %s already exist.", key));
      return;
     }

   AddKey(key);
   inputs = inputs + StringFormat(input_string_formatter, key, value) + ", ";
  }
//+------------------------------------------------------------------+
//| Add double input                                                 |
//+------------------------------------------------------------------+
void CInputsProvider::InputDouble(const string key, const double value)
  {
   if(is_builed)
     {
      Alert("Build() already done. Method Build() can be called only once.");
      return;
     }

   if(ContainKey(key))
     {
      Alert(StringFormat("Error add input property! Key: %s already exist.", key));
      return;
     }

   AddKey(key);
   inputs = inputs + StringFormat(input_double_formatter, key, value) + ", ";
  }
//+------------------------------------------------------------------+
//| Add long input                                                   |
//+------------------------------------------------------------------+
void CInputsProvider::InputLong(const string key, const long value)
  {
   if(is_builed)
     {
      Alert("Build() already done. Method Build() can be called only once.");
      return;
     }

   if(ContainKey(key))
     {
      Alert(StringFormat("Error add input property! Key: %s already exist.", key));
      return;
     }

   AddKey(key);
   inputs = inputs + StringFormat(input_long_formatter, key, value) + ", ";
  }
//+------------------------------------------------------------------+
//| Add int input                                                                 |
//+------------------------------------------------------------------+
void CInputsProvider::InputInteger(const string key, const int value)
  {
   InputLong(key, (long) value);
  }
//+------------------------------------------------------------------+
//| Add time datetime input                                          |
//+------------------------------------------------------------------+
void CInputsProvider::InputDatetime(const string key, const datetime date)
  {
   InputLong(key, (long) date * 1000);
  }
//+------------------------------------------------------------------+
//| Add time seconds input                                           |
//+------------------------------------------------------------------+
void CInputsProvider::InputTimeSeconds(const string key, const int seconds)
  {
   InputLong(key, (long) seconds * 1000);
  }
//+------------------------------------------------------------------+
//| Add time milliseconds input                                      |
//+------------------------------------------------------------------+
void CInputsProvider::InputTimeMilliseconds(const string key, const long milliseconds)
  {
   InputLong(key, milliseconds);
  }
//+------------------------------------------------------------------+
//| return inputs as string (not valid JSON just for logging and debug)|
//+------------------------------------------------------------------+
string CInputsProvider::ToString(void)
  {
   return inputs;
  }
//+------------------------------------------------------------------+
//| Add key                                                          |
//+------------------------------------------------------------------+
void CInputsProvider::AddKey(const string key)
  {
   int size = ArraySize(keys);
   ArrayResize(keys, size + 1);
   keys[size] = key;
  }
//+------------------------------------------------------------------+
//| Check kay already exist                                          |
//+------------------------------------------------------------------+
bool CInputsProvider::ContainKey(const string key)
  {
   for(int i = 0; i < ArraySize(keys); i++)
      if(keys[i] == key)
         return true;

   return false;
  }
//+------------------------------------------------------------------+