#!/usr/bin/env python
import csv
import sys

fpath=sys.argv[1]
print "Reading %s" % fpath
f = open( fpath )
lines = csv.reader(f, delimiter='\t')
r=0
for row in lines:
    print "ROW:%s TEXT:%s(...) LEN:%d" % (r, row[1][0:30] ,len(row[1]))
    r=r+1
    if r > 20: break


