#!/usr/bin/env python

import os, sys
from Tkinter import *

class view:
    def __init__(self, progress_file):
        self.progress_file = progress_file
        
        self.root = Tk()
        self.root.title('Darklight-Nova VulnView')
        self.root.columnconfigure(0, weight=1)
        self.root.rowconfigure(0, weight=1)

        self.main_frame = Frame(self.root)
        self.main_frame.grid(column=0, row=0, sticky=(N, W, E, S))
        self.main_frame.columnconfigure(0, weight=3)
        self.main_frame.columnconfigure(1, weight=1)

        self.list_box = Listbox(self.main_frame, width=75, height=20, selectmode=SINGLE, activestyle='none')
        self.list_box.grid(column=0, row=0, sticky=(N, W, E, S))
        self.list_box.bind('<ButtonRelease-1>', self.load_desc)

        self.refresh()

        self.desc = Text(self.main_frame, width=50, state=DISABLED)
        self.desc.grid(column=1, row=0, sticky=(N, E, S))
        self.load_desc()
        
        Button(self.main_frame, text='Refresh', command=self.refresh, width=20).grid(column=0, row=3, padx=10, pady=5, sticky=(S, W))
        Button(self.main_frame, text='Exit', command=sys.exit, width=20).grid(column=1, row=3, padx=10, pady=5, sticky=(S, E))
        
        self.main_frame.pack()
        self.main_frame.focus_set()

        self.root.mainloop()

    def refresh(self):
        self.list_box.delete(0, self.list_box.size()+1)
        self.vulns = {}
        lines = []
        with open(self.progress_file, 'r') as f:
            lines = f.readlines()
        for i in range(len(lines)):
            if lines[i][-1] == '\n':
                lines[i] = lines[i][:-1]
        for line in lines:
            if not line.isspace() and not line == '':
                self.vulns[line[:line.find(': ')]] = line[line.find(': ')+2:]
        i = 0
        for key in sorted(self.vulns):
            self.list_box.insert(i, key)
            i+=1

    def load_desc(self, event=None):
        vuln = ''
        if (len(self.list_box.curselection())) >= 1:
            vuln = sorted(self.vulns)[int(self.list_box.curselection()[0])]
            text = self.vulns[sorted(self.vulns)[int(self.list_box.curselection()[0])]]
        else:
            text = 'Please select a vulnerability on the left to view its description here'
        self.desc.config(state=NORMAL)
        try:
            self.desc.delete(1.0, 10.0)
        except Exception as e:
            pass
        if vuln != '':
            self.desc.insert(1.0, vuln + '\n')
            self.desc.tag_add('name', 1.0, float(('1.{0}'.format(len(vuln)))))
            self.desc.tag_config('name', background='yellow')
        self.desc.insert(2.0, text)
        self.desc.config(state=DISABLED)
        

if __name__ == '__main__':
    view(sys.argv[1])
