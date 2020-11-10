#property copyright "Copyright © 2006-2017"
#property version "1.12"
#property strict

class PositionProvider
  {

   int               _magic;

   Position          _positionsCahe[];

   Position          _openCahe[];

   Position          _historyCahe[];

public:
                     PositionProvider(long magic) { _magic = magic; }
                    ~PositionProvider() {}

public:

   string            GetPositions()
     {
      Refresh();
      // todo impl
      return "{ \"open\": [], \"history\": [] }";
     }

private:

   void              Refresh()
     {

      // Fetch and Sort this.positions to this.open and this.closed
     }

};