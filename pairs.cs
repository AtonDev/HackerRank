using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;

namespace KDifference
{
   class Solution
   {
      static void Main(string[] args)
      {
         char[] space = { ' ' };

         string[] NK = Console.ReadLine().Split(space);
         int N = Int32.Parse(NK[0]), difference = Int32.Parse(NK[1]);

         int[] sequence = Console.ReadLine().Split(space, N).Select(x => Int32.Parse(x)).OrderBy(x => x).ToArray();

         int result = sequence.Intersect(from item in sequence select item + difference).Count();

         Console.Write(result);
      }
   }
}