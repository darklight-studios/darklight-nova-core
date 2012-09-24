#!/usr/bin/env python

import sys
import os

WINDOWS = 'win' in sys.platform
INSTRUCTIONS = ['REPL', 'PUSH', 'CMD', 'INSTALL']
MODES = ['CORE', 'HEADLESS', 'LIVE']
METHODS = ['COMPILE', 'JAR', 'SOURCE']


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
                if len(instruction) >= 2:
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
        kwargs = {}
        for arg in args:
            if arg in METHODS:
                kwargs['method'] = arg
            elif arg in MODES:
                kwargs['mode'] = arg
        if kwargs['method'] == 'JAR':
            if os.path.exists(args[-1]):
                kwargs['manifest'] = args[-1]
            else:
                if kwargs['mode'] == 'CORE':
                    #kwargs['manifest'] = os.path.realpath('Manifest-Core.MF')
                    kwargs['manifest'] = 'Manifest-Core.MF'
                elif kwargs['mode'] == 'HEADLESS':
                    #kwargs['manifest'] = os.path.realpath('Manifest-Headless.MF')
                    kwargs['manifest'] = 'Manifest-Headless.MF'
                elif kwargs['mode'] == 'LIVE':
                    #kwargs['manifest'] = os.path.realpath('Manifest-Live.MF')
                    kwargs['manifest'] = 'Manifest-Live.MF'
                    
        dest_dir = args[0]
        
        # EXCLUDE FOR TESTING
        '''if os.path.exists(dest_dir):
            if WINDOWS:
                self.CMD(['rmdir', '/S', '/Q', dest_dir])
            else:
                self.CMD(['rm', '-rf', dest_dir])'''
                
        self.CMD(['mkdir', dest_dir])

        if kwargs.has_key('mode') and kwargs.has_key('method'):
            if kwargs['mode'] == 'CORE':
                self.REPL(['VulnView.py', os.path.join(dest_dir, 'VulnView.pyw')])

            if kwargs['method'] == 'COMPILE':
                self.CMD(['mkdir', os.path.join(dest_dir, 'bin')])
                if kwargs['mode'] == 'CORE':
                    self.CMD(['javac', '-classpath', 'src', '-d', os.path.join(dest_dir, 'bin'), 'src\com\ijg\darklightnova\core\Engine.java'])
                elif kwargs['mode'] == 'HEADLESS':
                    self.CMD(['javac', '-classpath', 'src', '-d', os.path.join(dest_dir, 'bin'), 'src\com\ijg\darklightnova\headless\Engine.java'])
                elif kwargs['mode'] == 'LIVE':
                    self.CMD(['javac', '-classpath', 'src', '-d', os.path.join(dest_dir, 'bin'), 'src\com\ijg\darklightnova\live\Engine.java'])

            elif kwargs['method'] == 'JAR' and kwargs.has_key('manifest'):
                self.CMD(['mkdir', os.path.realpath('bin')])
                if kwargs['mode'] == 'CORE':
                    self.CMD(['javac', '-classpath', 'src', '-d', os.path.realpath('bin'), 'src\com\ijg\darklightnova\core\Engine.java'])
                elif kwargs['mode'] == 'HEADLESS':
                    self.CMD(['javac', '-classpath', 'src', '-d', os.path.realpath('bin'), 'src\com\ijg\darklightnova\headless\Engine.java'])
                elif kwargs['mode'] == 'LIVE':
                    self.CMD(['javac', '-classpath', 'src', '-d', os.path.realpath('bin'), 'src\com\ijg\darklightnova\live\Engine.java'])
                self.CMD(['jar', 'cfm', 'Darklight.jar', kwargs['manifest'], 'bin'])
                self.PUSH(['Darklight.jar', dest_dir])

                # EXCLUDE FOR TESTING
                '''if WINDOWS:
                    self.CMD(['rmdir bin /S /Q'])
                else:
                    self.CMD(['rm -rf bin'])'''
                    
        elif kwargs.has_key('method'):
            if kwargs['method'] == 'SOURCE':
                if WINDOWS:
                    self.CMD(['xcopy', 'src', os.path.join(dest_dir, os.path.pathsep), '/S', '/Y'])
                else:
                    self.CMD(['cp', '-rf', 'src', dest_dir])

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
