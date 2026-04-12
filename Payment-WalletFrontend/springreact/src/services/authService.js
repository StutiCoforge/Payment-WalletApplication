import api from './api';

export const login = (data) => api.post('/customers/login', data);
export const signup = (data) => api.post('/customers/signup', data);
export const sendOtp = (data) => api.post('/customers/send-otp', data);
export const verifyOtp = (data) => api.post('/customers/verify-otp', data);
export const forgetPassword = (data) => api.post('/customers/forget-password', data);
export const resetPassword = (data) => api.post('/auth/customers/reset-password', data);
export const adminLogin = (data) => api.post('/customers/admin/login', data);
export const getCustomerDetails = () => api.get('/auth/customers/getDetails');
