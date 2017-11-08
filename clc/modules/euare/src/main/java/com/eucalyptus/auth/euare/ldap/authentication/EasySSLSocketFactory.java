/*************************************************************************
 * Copyright 2008 Regents of the University of California
 * Copyright 2009-2012 Ent. Services Development Corporation LP
 *
 * Redistribution and use of this software in source and binary forms,
 * with or without modification, are permitted provided that the
 * following conditions are met:
 *
 *   Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 *   Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer
 *   in the documentation and/or other materials provided with the
 *   distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE. USERS OF THIS SOFTWARE ACKNOWLEDGE
 * THE POSSIBLE PRESENCE OF OTHER OPEN SOURCE LICENSED MATERIAL,
 * COPYRIGHTED MATERIAL OR PATENTED MATERIAL IN THIS SOFTWARE,
 * AND IF ANY SUCH MATERIAL IS DISCOVERED THE PARTY DISCOVERING
 * IT MAY INFORM DR. RICH WOLSKI AT THE UNIVERSITY OF CALIFORNIA,
 * SANTA BARBARA WHO WILL THEN ASCERTAIN THE MOST APPROPRIATE REMEDY,
 * WHICH IN THE REGENTS' DISCRETION MAY INCLUDE, WITHOUT LIMITATION,
 * REPLACEMENT OF THE CODE SO IDENTIFIED, LICENSING OF THE CODE SO
 * IDENTIFIED, OR WITHDRAWAL OF THE CODE CAPABILITY TO THE EXTENT
 * NEEDED TO COMPLY WITH ANY SUCH LICENSES OR RIGHTS.
 ************************************************************************/

package com.eucalyptus.auth.euare.ldap.authentication;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.log4j.Logger;

/**
 * An SSLSocketFactory that ignores certificate validation.
 */
public class EasySSLSocketFactory extends SSLSocketFactory {
  
  public static class DummyTrustManager implements X509TrustManager {

    @Override
    public void checkClientTrusted( X509Certificate[] arg0, String arg1 ) throws CertificateException {
      // do nothing
    }

    @Override
    public void checkServerTrusted( X509Certificate[] arg0, String arg1 ) throws CertificateException {
      // do nothing
    }

    @Override
    public X509Certificate[] getAcceptedIssuers( ) {
      return new X509Certificate[0];
    }
    
  }
  
  public static SocketFactory getDefault( ) {
    return new EasySSLSocketFactory( );
  }
  
  private static final Logger LOG = Logger.getLogger( EasySSLSocketFactory.class );
  
  private SSLSocketFactory socketFactory;
  
  public EasySSLSocketFactory( ) {
    try {
      SSLContext ctx = SSLContext.getInstance( "TLS" );
      ctx.init( null, new TrustManager[]{ new DummyTrustManager( ) }, new SecureRandom( ) );
      socketFactory = ctx.getSocketFactory( );
    } catch ( Exception e ) {
      LOG.error( e, e );
    }
  }
  @Override
  public Socket createSocket( Socket socket, String host, int port, boolean autoClose ) throws IOException {
    return socketFactory != null ? socketFactory.createSocket( socket, host, port, autoClose ) : null;
  }
  
  @Override
  public String[] getDefaultCipherSuites( ) {
    return socketFactory != null ? socketFactory.getDefaultCipherSuites( ) : null;
  }
  
  @Override
  public String[] getSupportedCipherSuites( ) {
    return socketFactory != null ? socketFactory.getSupportedCipherSuites( ) : null;
  }
  
  @Override
  public Socket createSocket( String host, int port ) throws IOException, UnknownHostException {
    return socketFactory != null ? socketFactory.createSocket( host, port ) : null;
  }
  
  @Override
  public Socket createSocket( InetAddress host, int port ) throws IOException {
    return socketFactory != null ? socketFactory.createSocket( host, port ) : null;
  }
  
  @Override
  public Socket createSocket( String host, int port, InetAddress localHost, int localPort ) throws IOException, UnknownHostException {
    return socketFactory != null ? socketFactory.createSocket( host, port, localHost, localPort ) : null;
  }
  
  @Override
  public Socket createSocket( InetAddress address, int port, InetAddress localAddress, int localPort ) throws IOException {
    return socketFactory != null ? socketFactory.createSocket( address, port, localAddress, localPort ) : null;
  }
  
}
