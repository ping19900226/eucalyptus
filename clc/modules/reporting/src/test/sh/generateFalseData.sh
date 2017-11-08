#!/bin/sh

# Copyright 2009-2012 Ent. Services Development Corporation LP
#
# Redistribution and use of this software in source and binary forms,
# with or without modification, are permitted provided that the
# following conditions are met:
#
#   Redistributions of source code must retain the above copyright
#   notice, this list of conditions and the following disclaimer.
#
#   Redistributions in binary form must reproduce the above copyright
#   notice, this list of conditions and the following disclaimer
#   in the documentation and/or other materials provided with the
#   distribution.
#
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
# "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
# LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
# FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
# COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
# INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
# BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
# LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
# CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
# LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
# ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
# POSSIBILITY OF SUCH DAMAGE.
#
# This file may incorporate work covered under the following copyright
# and permission notice:
#
#   Software License Agreement (BSD License)
#
#   Copyright (c) 2008, Regents of the University of California
#   All rights reserved.
#
#   Redistribution and use of this software in source and binary forms,
#   with or without modification, are permitted provided that the
#   following conditions are met:
#
#     Redistributions of source code must retain the above copyright
#     notice, this list of conditions and the following disclaimer.
#
#     Redistributions in binary form must reproduce the above copyright
#     notice, this list of conditions and the following disclaimer
#     in the documentation and/or other materials provided with the
#     distribution.
#
#   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
#   "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
#   LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
#   FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
#   COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
#   INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
#   BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
#   LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
#   CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
#   LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
#   ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
#   POSSIBILITY OF SUCH DAMAGE. USERS OF THIS SOFTWARE ACKNOWLEDGE
#   THE POSSIBLE PRESENCE OF OTHER OPEN SOURCE LICENSED MATERIAL,
#   COPYRIGHTED MATERIAL OR PATENTED MATERIAL IN THIS SOFTWARE,
#   AND IF ANY SUCH MATERIAL IS DISCOVERED THE PARTY DISCOVERING
#   IT MAY INFORM DR. RICH WOLSKI AT THE UNIVERSITY OF CALIFORNIA,
#   SANTA BARBARA WHO WILL THEN ASCERTAIN THE MOST APPROPRIATE REMEDY,
#   WHICH IN THE REGENTS' DISCRETION MAY INCLUDE, WITHOUT LIMITATION,
#   REPLACEMENT OF THE CODE SO IDENTIFIED, LICENSING OF THE CODE SO
#   IDENTIFIED, OR WITHDRAWAL OF THE CODE CAPABILITY TO THE EXTENT
#   NEEDED TO COMPLY WITH ANY SUCH LICENSES OR RIGHTS.

# Generates fake reporting data
#
# Be sure to build the test classes and install them before running this as it
#  relies upon them. From /clc, execute ant build-test install

CLC_IP="localhost"

if [ -z "$1" ]
then
	echo "arg missing: admin pw"
	exit -1
fi

# Login, and get session id
wget -O /tmp/sessionId --no-check-certificate "https://$CLC_IP:8443/loginservlet?adminPw=$1"
if [ "$?" -ne "0" ]; then echo "Login failed"; exit 1; fi
SESSIONID=`cat /tmp/sessionId`
echo "session id:" $SESSIONID

# Clear and generate false data for instances
wget --no-check-certificate -O /tmp/nothing "https://$CLC_IP:8443/commandservlet?sessionId=$SESSIONID&className=com.eucalyptus.reporting.instance.FalseDataGenerator&methodName=removeFalseData"
if [ "$?" -ne "0" ]; then echo "Instance data removal failed"; exit 1; fi
wget --no-check-certificate -O /tmp/nothing "https://$CLC_IP:8443/commandservlet?sessionId=$SESSIONID&className=com.eucalyptus.reporting.instance.FalseDataGenerator&methodName=generateFalseData"
if [ "$?" -ne "0" ]; then echo "Instance data generation failed"; exit 1; fi


# Clear and generate false data for storages
wget --no-check-certificate -O /tmp/nothing "https://$CLC_IP:8443/commandservlet?sessionId=$SESSIONID&className=com.eucalyptus.reporting.storage.FalseDataGenerator&methodName=removeFalseData"
if [ "$?" -ne "0" ]; then echo "Storage data removal failed"; exit 1; fi
wget --no-check-certificate -O /tmp/nothing "https://$CLC_IP:8443/commandservlet?sessionId=$SESSIONID&className=com.eucalyptus.reporting.storage.FalseDataGenerator&methodName=generateFalseData"
if [ "$?" -ne "0" ]; then echo "Storage data generation failed"; exit 1; fi


# Clear and generate false data for s3
wget --no-check-certificate -O /tmp/nothing "https://$CLC_IP:8443/commandservlet?sessionId=$SESSIONID&className=com.eucalyptus.reporting.s3.FalseDataGenerator&methodName=removeFalseData"
if [ "$?" -ne "0" ]; then echo "S3 data removal failed"; exit 1; fi
wget --no-check-certificate -O /tmp/nothing "https://$CLC_IP:8443/commandservlet?sessionId=$SESSIONID&className=com.eucalyptus.reporting.s3.FalseDataGenerator&methodName=generateFalseData"
if [ "$?" -ne "0" ]; then echo "S3 data generation failed"; exit 1; fi
