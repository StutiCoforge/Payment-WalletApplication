import React, { createContext, useContext, useState, useEffect, useCallback } from 'react';
import { login, signup, adminLogin, getCustomerDetails, sendOtp, verifyOtp, forgetPassword, resetPassword } from '../services/authService';
import { setToken, getToken, removeToken, setUser, getUser } from '../utils/auth';

const AuthContext = createContext(null);

export const useAuth = () => {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error('useAuth must be inside AuthProvider');
  return ctx;
};

export const AuthProvider = ({ children }) => {
  const [user, setUserState] = useState(getUser());
  const [token, setTokenState] = useState(getToken());
  const [isLoading, setIsLoading] = useState(!!getToken());

  const refreshUser = useCallback(async () => {
    try {
      const data = await getCustomerDetails();
      setUserState(data.data);
      setUser(data.data);
    } catch {
      setUserState(null);
      setTokenState(null);
      removeToken();
    }
  }, []);

  useEffect(() => {
    if (!token) {
      setIsLoading(false);
      return;
    }

    if (user?.role === 'ADMIN') {
      setIsLoading(false);
      return;
    }

    refreshUser().finally(() => setIsLoading(false));
  }, [token, refreshUser, user?.role]);

  const handleLogin = async (email, pwd) => {
    const res = await login({ email, pwd });
    const customer = res.data.customer || { name: email, role: 'CUSTOMER' };
    setToken(res.data.token);
    setTokenState(res.data.token);
    setUserState(customer);
    setUser(customer);
  };


  const handleAdminLogin = async (email, pwd) => {
    // return new Promise((resolve, reject) => {
    //   if (username === 'admin' && password === 'admin123') {
    //     setUser({ username: 'admin', role: 'ADMIN' });
    //     resolve(true);
    //   } else {
    //     reject({ message: 'Invalid admin credentials' });
    //   }
    // });

    const res = await adminLogin({ email, pwd });
    const admin = res.data.admin || { name: email, role: 'ADMIN' };
    setToken(res.data.token);
    setTokenState(res.data.token);
    setUserState(admin);
    setUser(admin);
  };


  const handleSignup = async (data) => {
    const res = await signup(data);
    setToken(res.data.token);
    setTokenState(res.data.token);
    setUserState(res.data.customer || { name: data.email });
    setUser(res.data.customer || { name: data.email });
  };
  
  const handleSendOtp = async (data) => {
    const res = await sendOtp(data);
    localStorage.setItem("otp",res.data.otp);    
  };

  const handleVerifyOtp = async (data) => {
    const otp = localStorage.getItem("otp");    
    await verifyOtp({...data,"otpToken":otp});
  };
  
  const handleForgetPassword = async (data) => {
    const otp = localStorage.getItem("otp");    
    await forgetPassword({...data,"otpToken":otp});
  };
  
  const handleResetPassword = async (data) => {
    await resetPassword({...data});
  };

  const handleLogout = () => {
    removeToken();
    setTokenState(null);
    setUserState(null);
  };

  return (
    <AuthContext.Provider value={{
      user,
      token,
      isLoading,
      login: handleLogin,
      adminLogin: handleAdminLogin,
      signup: handleSignup,
      handleSendOtp: handleSendOtp,
      handleVerifyOtp: handleVerifyOtp,
      forgetPassword: handleForgetPassword,
      resetPassword: handleResetPassword,
      logout: handleLogout,
      refreshUser
    }}>
      {children}
    </AuthContext.Provider>
  );
};