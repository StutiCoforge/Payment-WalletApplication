import React, { useEffect, useState } from 'react';
import { Row, Col, Card, Alert, Badge, Button, Container, Form } from 'react-bootstrap';
import Layout from '../components/Layout';
import Loader from '../components/Loader';
import { getCustomerDetails } from '../services/authService';
import { getWallet } from '../services/walletService';
import { getAllTransactions } from '../services/transactionService';
import { useAuth } from '../contexts/AuthContext';

const StatCard = ({ title, value, icon, color }) => (
  <Card className={`border-0 shadow-sm rounded-4 text-white bg-${color}`}>
    <Card.Body className="d-flex align-items-center gap-3 p-4">
      <i className={`bi ${icon}`} style={{ fontSize: '2.2rem', opacity: 0.85 }}></i>
      <div>
        <div className="small fw-semibold opacity-75">{title}</div>
        <div className="fs-4 fw-bold">{value}</div>
      </div>
    </Card.Body>
  </Card>
);

const Dashboard = () => {
  const { resetPassword } = useAuth();
  const [customer, setCustomer] = useState(null);
  const [wallet, setWallet] = useState(null);
  const [transactions, setTransactions] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  const [resetPasswordloading, setResetPasswordLoading] = useState(false);
  const [resetPasswordError, setResetPasswordError] = useState('');

  const [showResetPasswordForm, setShowResetPasswordForm] = useState(false);
  const [resetPasswordForm, setResetPasswordForm] = useState({
    pwd: "",
    newPwd: ""
  })

  useEffect(() => {
    const fetchAll = async () => {
      try {
        const [custRes, walletRes, txRes] = await Promise.all([
          getCustomerDetails(),
          getWallet(),
          getAllTransactions(),
        ]);
        setCustomer(custRes.data);
        setWallet(walletRes.data);
        setTransactions(txRes.data || []);
      } catch (err) {
        setError('Failed to load dashboard data.');
      } finally {
        setLoading(false);
      }
    };
    fetchAll();
  }, []);

  const handleResetPasswordChange=(e) =>{ 
    setResetPasswordForm({ ...resetPasswordForm, [e.target.name]: e.target.value });
    setResetPasswordError("")
  }

  const handleResetPassword = async (e) => {
    e.preventDefault();
    setResetPasswordError('');
    if (!resetPasswordForm.pwd || !resetPasswordForm.newPwd) {
      setError('All fields are required.');
      return;
    }
    setResetPasswordLoading(true);
    try {
      await resetPassword({ pwd: resetPasswordForm.pwd, newPwd: resetPasswordForm.newPwd });
      setShowResetPasswordForm(false);
    } catch (err) {
      setResetPasswordError(err.response?.data?.message || 'Invalid Current Password. Please try again.');
    } finally {
      setResetPasswordLoading(false);
    }
  };


  if (loading) return <Layout><Loader /></Layout>;

  return (
    <>
      <Layout>
        <h4 className="fw-bold mb-4 text-primary">Dashboard</h4>
        {error && <Alert variant="danger">{error}</Alert>}

        <Row className="g-4 mb-4">
          <Col md={3}>
            <StatCard title="Wallet Balance" value={`₹${wallet?.balance ?? '0.00'}`} icon="bi-wallet2" color="primary" />
          </Col>
          <Col md={3}>
            <StatCard title="Total Transactions" value={transactions.length} icon="bi-arrow-left-right" color="success" />
          </Col>
          <Col md={3}>
            <StatCard title="Account Status" value={customer?.status || 'Active'} icon="bi-person-check" color="info" />
          </Col>
          <Col md={3}>
            <StatCard title="Wallet ID" value={wallet?.walletId || 'N/A'} icon="bi-credit-card" color="warning" />
          </Col>
        </Row>

        <Row className="g-4">
          <Col md={5}>
            <Card className="border-0 shadow-sm rounded-4 h-100">
              <Card.Header className="bg-white border-0 fw-bold pt-3">Customer Details</Card.Header>
              <Card.Body>
                {customer ? (
                  <>
                    <table className="table table-borderless mb-0">
                      <tbody>
                        {[
                          ['Name', customer.custName || customer.name || customer.customerName],
                          ['Email', customer.email || customer.username || customer.userEmail],
                          ['Phone', customer.mobileNumber || customer.phone || customer.mobile],
                        ].map(([k, v]) => (
                          <tr key={k}>
                            <td className="text-muted fw-semibold" style={{ width: '40%' }}>{k}</td>
                            <td>{v || '—'}</td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                    <Button type='button' className='btn btn-danger mt-2' onClick={() => setShowResetPasswordForm(true)}>
                      Reset Password
                    </Button>
                  </>
                ) : <p className="text-muted">No details available.</p>}
              </Card.Body>
            </Card>
          </Col>
          <Col md={7}>
            <Card className="border-0 shadow-sm rounded-4 h-100">
              <Card.Header className="bg-white border-0 fw-bold pt-3">
                Recent Transactions
              </Card.Header>

              <Card.Body className="p-0">
                {transactions.length === 0 ? (
                  <p className="text-muted p-3">No transactions yet.</p>
                ) : (
                  <table className="table table-hover mb-0">
                    <thead className="table-light">
                      <tr>
                        <th>Type</th>
                        <th>Amount</th>
                        <th>Category</th>
                        <th>Date</th>
                      </tr>
                    </thead>
                    <tbody>
                      {transactions.slice(0, 5).map((tx, i) => (
                        <tr key={tx.transactionId || i}>
                          <td>
                            <Badge
                              bg={tx.transactionType === 'CREDIT' ? 'success' : 'danger'}
                            >
                              {tx.transactionType}
                            </Badge>
                          </td>

                          <td
                            className={
                              tx.transactionType === 'CREDIT'
                                ? 'text-success fw-semibold'
                                : 'text-danger fw-semibold'
                            }
                          >
                            {tx.transactionType === 'CREDIT' ? '+' : '-'}₹
                            {tx.transactionAmount}
                          </td>

                          <td>{tx.category || '—'}</td>

                          <td>
                            {tx.transactionDate
                              ? tx.transactionDate.slice(0, 10)
                              : '—'}
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                )}
              </Card.Body>
            </Card>
          </Col>
        </Row>
      </Layout>

      {showResetPasswordForm && <div className='position-absolute vh-100 w-100 top-0 start-0' style={{background:"#000000c7"}}>
        <Container style={{ maxWidth: '420px',background:"white" }}>
          <div className="text-center mb-4">
            <i className="bi bi-wallet2 text-primary" style={{ fontSize: '2.5rem' }}></i>
            <h3 className="fw-bold text-primary mt-2">PayWallet</h3>
            <p className="text-muted">Reset Password</p>
          </div>

          <Card className="shadow border-0 rounded-4">
            <Card.Body className="p-4">
              {resetPasswordError && <Alert variant="danger">{resetPasswordError}</Alert>}
              <Form onSubmit={handleResetPassword}>
                <Form.Group className="mb-4">
                  <Form.Label>Current Password</Form.Label>
                  <Form.Control
                    type="password"
                    name="pwd"
                    placeholder="Enter Current password"
                    value={resetPasswordForm.pwd}
                    onChange={handleResetPasswordChange}
                  />
                </Form.Group>
                <Form.Group className="mb-4">
                  <Form.Label>New Password</Form.Label>
                  <Form.Control
                    type="password"
                    name="newPwd"
                    placeholder="Enter New password"
                    value={resetPasswordForm.newPwd}
                    onChange={handleResetPasswordChange}
                    required
                    pattern="(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@.#$!%*?&])[A-Za-z\d@.#$!%*?&]{8,15}"
                    onInvalid={()=>setResetPasswordError("Password should have atleast 1 uppercase, 1 lowercase, 1 digit and 1 special character")}
                  />
                </Form.Group>

                <Button
                  type="submit"
                  variant="primary"
                  className="w-100 fw-semibold"
                  disabled={resetPasswordloading}
                >
                  {resetPasswordloading ? 'Verifying...' : 'Reset Password'}
                </Button>
              </Form>
            </Card.Body>
          </Card>
        </Container>
      </div>}
    </>


  );
};

export default Dashboard;
