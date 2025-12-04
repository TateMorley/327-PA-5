#!/usr/bin/python
import re

def regex_example ():
    line = "2Cab 4 Level smarter than 3 dogs";

    myregex = r'([^D-Z]*)([0-9][0-9]*)'

    #
    # There are two different ways to do regex search in Python; Just pick one, but not both
    #
    # 1. The following code demonstrates how to get the matching results in just one call
    #
    x = re.findall (myregex, line)
    print ('result1 = ', x, '\n')

    #
    # 2. The following code demonstrates how to get the matching results iteratively
    #
    allSearchObjs = re.finditer(myregex, line) 
    # allSearchObjs = re.finditer( myregex, line, re.M|re.I|re.X) 
    #   re.A (ASCII-only matching), 
    #   re.I (ignore case), 
    #   re.L (locale dependent), 
    #   re.M (multi-line), 
    #   re.S (dot matches all), 
    #   re.U (Unicode matching), 
    #   and re.X (verbose),
    #

    print ('result2 = ')
    for searchObj in allSearchObjs:
        # print ("searchObj.group(): ", '|', searchObj.group(), '|')
        print ("\tsearchObj.group(0): ", searchObj.group(0))
        print ("\tsearchObj.group(1): ", searchObj.group(1))
        print ("\tsearchObj.group(2): ", searchObj.group(2))
        s = searchObj.start()
        e = searchObj.end()
        print ('\tString match "%s" at %d:%d' % (line[s:e], s, e))
        print ('')

def main():
    regex_example()

if __name__ == "__main__":
    main()
