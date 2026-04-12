import api from './api';

export const adminLogin = (data) => api.post('/customers/admin/login', data);
// export const getAdminDashboard = () => api.get('/admin/dashboard');
export const getAllCustomers = () => api.get('/admin/customers');
export const getAllWallets = () => api.get('/admin/wallets');
export const getAllTransactionsAdmin = (params = {}) => api.get('/admin/transactions/all', { params });
export const searchTransactionsAdmin = (params = {}) => api.get('/admin/transactions/search', { params });
export const getAllTransactionsCategoryAdmin = (category = {}) => api.get(`/admin/transactions/category/${category}`);
export const getAllTransactionsCategoryAndDateAdmin = (params = {}) => api.get(`/admin/transactions/category/${params.category}/dates?from=${params.fromDate}&&to=${params.toDate}`);
export const getAllTransactionsSubCategoryAdmin = (subcategory = {}) => api.get(`/admin/transactions/subcategory/${subcategory}`);
export const getAllTransactionsSubCategoryAndDateAdmin = (params = {}) => api.get(`/admin/transactions/subcategory/${params.subCategory}/dates?from=${params.fromDate}&&to=${params.toDate}`);
export const getAllTransactionsByDateAdmin = (params = {}) => api.get(`/admin/transactions/dates?from=${params.fromDate}&&to=${params.toDate}`);
export const getAllBeneficiaries = () => api.get('/admin/beneficiaries');
export const blockCustomer = (id) => api.put(`/admin/blockCustomer/${id}`);
export const deleteCustomer = (id) => api.delete(`/admin/customers/${id}`);
