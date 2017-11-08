/*************************************************************************
 * Copyright 2008 Regents of the University of California
 * Copyright 2009-2013 Ent. Services Development Corporation LP
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
package com.eucalyptus.component.groups;

import java.util.Collection;

import com.eucalyptus.component.ServiceBuilder;
import com.eucalyptus.component.ServiceConfiguration;

/**
 * @author chris grzegorczyk <grze@eucalyptus.com>
 */
public interface ServiceGroupBuilder<T extends ServiceGroupConfiguration> extends ServiceBuilder<T> {
  
  /**
   * Get the list of member {@link com.eucalyptus.component.ServiceConfiguration}s which
   * should be acted on along with the group configuration {@code config}.
   * 
   * This method is invoked when the {@code config} is deregistered.
   * 
   * @param config
   * @return Set of ServiceConfigurations which are to be stored as part of this group.
   */
  Collection<ServiceConfiguration> onDeregister( T config );
  
  /**
   * Get the list of member {@link com.eucalyptus.component.ServiceConfiguration}s which
   * should be acted on along with the group configuration {@code config}.
   * 
   * This method is invoked when the {@code config} is registered.
   * 
   * @param config
   * @return Set of ServiceConfigurations which are to be stored as part of this group.
   */
  Collection<ServiceConfiguration> onRegister( T config );
  
  /**
   * Get the list of member {@link com.eucalyptus.component.ServiceConfiguration}s which
   * should be acted on along with the group configuration {@code config}.
   * 
   * This method is invoked when the {@code config} is updated.
   * 
   * @param config
   * @return Set of ServiceConfigurations which are to be stored as part of this group.
   */
  Collection<ServiceConfiguration> onUpdate( T config );
  
  /**
   * Get the list of member {@link com.eucalyptus.component.ServiceConfiguration}s which
   * should be acted on along with the group configuration {@code config}.
   * 
   * This method is invoked when the {@code config} is ENABLED.
   * 
   * @param config
   * @return Set of ServiceConfigurations which are to be stored as part of this group.
   */
  Collection<ServiceConfiguration> onEnabled( T config );
  
  /**
   * Get the list of member {@link com.eucalyptus.component.ServiceConfiguration}s which
   * should be acted on along with the group configuration {@code config}.
   * 
   * This method is invoked when the {@code config} is transitions to DISABLED.
   * 
   * @param config
   * @return Set of ServiceConfigurations which are to be stored as part of this group.
   */
  Collection<ServiceConfiguration> onDisabled( T config );
  
  /**
   * Get the list of member {@link com.eucalyptus.component.ServiceConfiguration}s which
   * should be acted on along with the group configuration {@code config}.
   * 
   * This method is invoked when the {@code config} is transitions to NOTREADY.
   * 
   * @param config
   * @return Set of ServiceConfigurations which are to be stored as part of this group.
   */
  Collection<ServiceConfiguration> onNotready( T config );
  
  /**
   * Get the list of member {@link com.eucalyptus.component.ServiceConfiguration}s which
   * should be acted on along with the group configuration {@code config}.
   * 
   * This method is invoked when the {@code config} is transitions to STARTED.
   * 
   * @param config
   * @return Set of ServiceConfigurations which are to be stored as part of this group.
   */
  Collection<ServiceConfiguration> onStarted( T config );
  
  /**
   * Get the list of member {@link com.eucalyptus.component.ServiceConfiguration}s which
   * should be acted on along with the group configuration {@code config}.
   * 
   * This method is invoked when the {@code config} is transitions to STOPPED.
   * 
   * @param config
   * @return Set of ServiceConfigurations which are to be stored as part of this group.
   */
  Collection<ServiceConfiguration> onStopped( T config );
  
  /**
   * This is to force usage of the {@link com.eucalyptus.component.groups.BaseServiceGroupBuilder}
   * 
   * @return
   */
  @Deprecated
  public abstract boolean __MUST_EXTEND_BASE_SERVICE_BUILDER( );
}
