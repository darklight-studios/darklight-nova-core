#!/usr/bin/env python

import sys
import os

INSTRUCTIONS = ['REPL', 'PUSH', 'CMD', 'INSTALL']

class Parser:
    def __init__(self, build_file):
        for i in range(len(build_file)):
            build_file[i] = build_file[i].strip('\n')
        self.build_file = build_file

    def valid_instruction(self, instruction):
        if instruction[0] in INSTRUCTIONS:
            if instruction[0] == 'REPL' or instruction[0] == 'PUSH':
                if len(instruction) == 3:
                    return True
            elif instruction[0] == 'INSTALL':
                if len(instruction) == 2:
                    return True
            elif instruction[0] == 'CMD':
                if len(instruction) >= 2:
                    return True
        return False

    def parse(self):
        instructions = []
        for line in self.build_file:
            line = line.split(' ')
            if self.valid_instruction(line):
                instructions.append(Worker(line[0], line[1:]))
        return instructions



class Worker:
    def __init__(self, action, args):
        self.action = action
        self.args = args

    def INSTALL(self, args):
        print('INSTALL: {0}'.format(args))
        dest_dir = 'C:\Program Files\Darklight-Nova' if 'win' in sys.platform else '/usr/local/Darklight-Nova'
        self.CMD(['mkdir', dest_dir])
        self.PUSH(['Darklight-Nova.jar', dest_dir])
        self.REPL(['VulnView.py', os.path.join(dest_dir, 'VulnewView.pyw')])

    def REPL(self, args):
        print('REPL: {0}'.format(args))
        if os.path.exists(args[0]) and os.path.isfile(args[0]):
            if os.path.exists(args[1]) and os.path.isfile(args[1]):
                if 'win' in sys.platform:
                    os.system('copy {0} {1} /Y'.format(args[0], args[1]))
                elif 'linux' in sys.platform:
                    os.system('cp {0} {1}'.format(args[0], args[1]))

    def PUSH(self, args):
        print('PUSH: {0}'.format(args))
        if os.path.exists(args[0]) and os.path.isfile(args[0]):
            if os.path.exists(args[1]) and os.path.isdir(args[1]):
                if 'win' in sys.platform:
                    os.system('copy {0} {1} /Y'.format(args[0], args[1]))
                elif 'linux' in sys.platform:
                    os.system('cp {0} {1}'.format(args[0], args[1]))

    def CMD(self, args):
        print('CMD: {0}'.format(args))
        os.system(' '.join(args))
    
    def act(self):
        eval('self.{0}({1})'.format(self.action, self.args))

    def __str__(self):
        return '{0}, {1}'.format(self.action, self.args)



if __name__ == '__main__':
    with open('BUILD') as build_file:
        workers = Parser(build_file.readlines()).parse()
    for worker in workers:
        worker.act()
