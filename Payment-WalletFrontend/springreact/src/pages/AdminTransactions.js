import React, { useState, useEffect } from 'react';
import { Table, Spinner, Form, Button, Row, Col, Card } from 'react-bootstrap';
import AdminLayout from '../components/AdminLayout';
import { getAllTransactionsAdmin, getAllTransactionsByDateAdmin, getAllTransactionsCategoryAdmin, getAllTransactionsCategoryAndDateAdmin, getAllTransactionsSubCategoryAdmin, getAllTransactionsSubCategoryAndDateAdmin, searchTransactionsAdmin } from '../services/adminService';

const AdminTransactions = () => {
  const [transactions, setTransactions] = useState([]);
  const [loading, setLoading] = useState(true);
  const [filters, setFilters] = useState({ search: '', category: '',subCategory: '',fromDate:"",toDate:"" });

  const loadTransactions = async (params = {}) => {
    setLoading(true);
    try {
      const res = await getAllTransactionsAdmin(params);
      setTransactions(res.data || []);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadTransactions();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    console.log(name,value)
    setFilters((prev) => ({ ...prev, [name]: value }));
  };

  const handleSearch = async (e) => {
    try {
      e.preventDefault();
      setLoading(true)
      const res = await searchTransactionsAdmin({ query: filters.search });
      setTransactions(res.data || []);
    } catch (err) {
      console.log(err);
    } finally{
      setLoading(false);
    }
  };
  
  const handleFilters = async (e) => {
    try {
      e.preventDefault();
      setLoading(true)

      let res = null;
      console.log(filters)
      
      if(filters.category && filters.fromDate && filters.toDate){
        console.log("getAllTransactionsCategoryAndDateAdmin")
        res = await getAllTransactionsCategoryAndDateAdmin(filters);
      }
      else if(filters.subCategory && filters.fromDate && filters.toDate){
        console.log("getAllTransactionsSubCategoryAndDateAdmin")
        res = await getAllTransactionsSubCategoryAndDateAdmin(filters);
      }
      else if(filters.category){
        console.log("getAllTransactionsCategoryAdmin")
        res = await getAllTransactionsCategoryAdmin(filters.category);
      }
      else if(filters.subCategory){
        console.log("getAllTransactionsSubCategoryAdmin")
        res = await getAllTransactionsSubCategoryAdmin(filters.subCategory);
      }
      else if(filters.fromDate && filters.toDate){
        console.log("getAllTransactionsByDateAdmin")
        res = await getAllTransactionsByDateAdmin(filters);
      }
      console.log("done")
      setTransactions(res.data || []);
    } catch (err) {
      console.log(err);
    } finally{
      setLoading(false);
    }
  };

  const handleReset = () => {
    const resetFilters = { search: '', category: '',subCategory: '',fromDate:"",toDate:"" };
    setFilters(resetFilters);
    loadTransactions(resetFilters);
  };

  return (
    <AdminLayout>
      <div className="mb-4">
        <h1 className="h3 fw-bold">Transaction History</h1>
        <p className="text-secondary mb-0">Filter and review all transactions made across the system.</p>
      </div>

      <Card className="shadow-sm rounded-4 bg-white border-0 mb-4">
        <Card.Body>
          <Form onSubmit={handleSearch}>
            <Row className="g-3 align-items-end">
              <Col md={9}>
                <Form.Group>
                  <Form.Label>Search</Form.Label>
                  <Form.Control
                    type="text"
                    name="search"
                    value={filters.search}
                    onChange={handleChange}
                    placeholder="Search by customer"
                  />
                </Form.Group>
              </Col>
              <Col md={3} className="d-flex gap-2">
                <Button type="submit" variant="primary" className="w-100">
                  Apply
                </Button>
                <Button variant="outline-secondary" className="w-100" onClick={handleReset}>
                  Reset
                </Button>
              </Col>
            </Row>
          </Form>
        </Card.Body>
      </Card>

      <Card className="shadow-sm rounded-4 bg-white border-0 mb-4">
        <Card.Body>
          <Form onSubmit={handleFilters}>
            <Row className="g-3 align-items-end">
              <Col md={2}>
                <Form.Group>
                  <Form.Label>Category</Form.Label>
                  <Form.Select name="category" value={filters.category} onChange={handleChange}>
                    <option value="">All</option>
                    <option value="BENEFICIARY_TRANSFER">Benificiary Transfer</option>
                    <option value="BILL_PAYMENT">Bill Payment</option>
                    <option value="WALLET_TOP_UP">Wallet Top Up</option>
                  </Form.Select>
                </Form.Group>
              </Col>
              <Col md={2}>
                <Form.Group>
                  <Form.Label>Sub Category</Form.Label>
                  <Form.Select name="subCategory" value={filters.subCategory} onChange={handleChange}>
                    <option value="">All</option>
                    <option value="ELECTRICITY">Electricity</option>
                    <option value="MOBILE_RECHARGE">Mobile Recharge</option>
                    <option value="GAS">Gas Cylinder</option>
                  </Form.Select>
                </Form.Group>
              </Col>

              <Col md={2}>
                <Form.Group>
                  <Form.Label>From Date</Form.Label>
                  <Form.Control name="fromDate" type='date' value={filters.fromDate} onChange={handleChange}>
                  </Form.Control>
                </Form.Group>
              </Col>

              <Col md={2}>
                <Form.Group>
                  <Form.Label>To Date</Form.Label>
                  <Form.Control name="toDate" type='date' value={filters.toDate} onChange={handleChange} >
                  </Form.Control>
                </Form.Group>
              </Col>

              <Col md={4} className="d-flex gap-2">
                <Button type="submit" variant="primary" className="w-100">
                  Apply
                </Button>
                <Button variant="outline-secondary" className="w-100" onClick={handleReset}>
                  Reset
                </Button>
              </Col>
            </Row>
          </Form>
        </Card.Body>
      </Card>

      {loading ? (
        <div className="d-flex justify-content-center py-5">
          <Spinner animation="border" variant="primary" />
        </div>
      ) : (
        <div className="table-responsive bg-white shadow-sm rounded-4 p-3">
          <div className='pb-2'><strong>Transactions found:</strong> {transactions.length}</div >
          <Table hover className="mb-0 align-middle">
            <thead className="table-light">
              <tr>
                <th>ID</th>
                <th>Type</th>
                <th>Amount</th>
                <th>Category</th>
                <th>Status</th>
                <th>Date</th>
              </tr>
            </thead>
            <tbody>
              {transactions.length === 0 ? (
                <tr>
                  <td colSpan="7" className="text-center py-4 text-muted">
                    No transactions found.
                  </td>
                </tr>
              ) : (
                transactions.map((tx) => (
                  <tr key={tx.transactionId}>
                    <td>{tx.transactionId || 'N/A'}</td>
                    <td> <span className={`btn ${tx.transactionType == "DEBIT" ? "btn-outline-danger" : "btn-outline-success"}`}>{tx.transactionType || 'N/A'} </span></td>
                    <td> {tx.transactionType == "DEBIT" ? "-" : "+"}  ₹{Number(tx.transactionAmount ?? 0).toFixed(2)}</td>
                    <td>{tx.category || 'N/A'}</td>
                    <td>{tx.transactionStatus || 'N/A'}</td>
                    <td>{new Date(tx.date || tx.createdAt || Date.now()).toLocaleString()}</td>
                  </tr>
                ))
              )}
            </tbody>
          </Table>
        </div>
      )}
    </AdminLayout>
  );
};


export default AdminTransactions;
