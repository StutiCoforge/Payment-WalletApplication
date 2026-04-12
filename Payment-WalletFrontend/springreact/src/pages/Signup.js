import React, { useState } from 'react';
import { Container, Card, Form, Button, Alert } from 'react-bootstrap';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';

const Signup = () => {
  const navigate = useNavigate();
  const { signup, handleSendOtp, handleVerifyOtp } = useAuth();
  const [form, setForm] = useState({ custName: '', email: '', pwd: '', mobileNumber: '' });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [loading, setLoading] = useState(false);
  const [showOtpForm, setShowOtpForm] = useState(false);
  const [otp, setOtp] = useState('');

  const handleChange = (e) =>{
    setForm({ ...form, [e.target.name]: e.target.value });
    setError("")
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    if (!form.custName || !form.email || !form.pwd) {
      setError('Name, email and password are required.');
      return;
    }
    try {
      setLoading(true);
      await handleSendOtp({ email: form.email });
      setShowOtpForm(true);
      setOtp("")
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to send OTP. Please try again.');
    } finally {
      setLoading(false);
    }
  }

  const handleSignup = async (e) => {
    e.preventDefault();
    setError('');
    if (!form.custName || !form.email || !form.pwd) {
      setError('Name, email and password are required.');
      return;
    }
    setLoading(true);
    try {
      await handleVerifyOtp({email:form.email,otp});

      await signup(form);
      setSuccess('Account created! Logging you in...');
      setTimeout(() => navigate('/dashboard'), 1500);
    } catch (err) {
      setError(err.response?.data?.message || 'Signup failed. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div
      className="d-flex align-items-center justify-content-center bg-light"
      style={{ minHeight: '100vh' }}
    >
      {showOtpForm === false ? <Container style={{ maxWidth: '440px' }}>
        <div className="text-center mb-4">
          <i className="bi bi-wallet2 text-primary" style={{ fontSize: '2.5rem' }}></i>
          <h3 className="fw-bold text-primary mt-2">PayWallet</h3>
          <p className="text-muted">Create your account</p>
        </div>
        <Card className="shadow border-0 rounded-4">
          <Card.Body className="p-4">
            {error && <Alert variant="danger">{error}</Alert>}
            {success && <Alert variant="success">{success}</Alert>}
            <Form onSubmit={handleSubmit}>
              <Form.Group className="mb-3">
                <Form.Label>Full Name</Form.Label>
                <Form.Control name="custName" placeholder="John Doe" required pattern='[a-zA-Z][a-zA-Z][a-zA-Z\s]{0,20}[a-zA-Z]' onInvalid={()=>setError("Please enter valid name")} value={form.custName} onChange={handleChange} />
              </Form.Group>
              <Form.Group className="mb-3">
                <Form.Label>Email</Form.Label>
                <Form.Control type="email" name="email" placeholder="john@example.com" required pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}" onInvalid={()=>setError("Please enter valid email")} value={form.email} onChange={handleChange} />
              </Form.Group>
              <Form.Group className="mb-3">
                <Form.Label>Mobile Number</Form.Label>
                <Form.Control name="mobileNumber" placeholder="9876543210" required value={form.mobileNumber} pattern="[6-9][0-9]{9}" onInvalid={()=>setError("Please enter valid mobile number")} onChange={handleChange} />
              </Form.Group>
              <Form.Group className="mb-4">
                <Form.Label>Password</Form.Label>
                <Form.Control type="password" name="pwd" placeholder="Password" required pattern="(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@.#$!%*?&])[A-Za-z\d@.#$!%*?&]{8,15}" onInvalid={()=>setError("Password should have atleast 1 uppercase, 1 lowercase, 1 digit and 1 special character")} value={form.pwd} onChange={handleChange} />
              </Form.Group>
              <Button type="submit" variant="primary" className="w-100 fw-semibold" disabled={loading}>
                {loading ? 'Sending OTP...' : 'Create Account'}
              </Button>
            </Form>
            <p className="text-center mt-3 mb-0 text-muted">
              Already have an account?{' '}
              <Link to="/login" className="text-primary fw-semibold">Sign In</Link>
            </p>
          </Card.Body>
        </Card>
      </Container> :

        <Container style={{ maxWidth: '440px' }}>
          <div className="text-center mb-4">
            <i className="bi bi-wallet2 text-primary" style={{ fontSize: '2.5rem' }}></i>
            <h3 className="fw-bold text-primary mt-2">PayWallet</h3>
            <p className="text-muted">Plese Enter OTP</p>
          </div>
          <Card className="shadow border-0 rounded-4">
            <Card.Body className="p-4">
              {error && <Alert variant="danger">{error}</Alert>}
              {success && <Alert variant="success">{success}</Alert>}
              <Form onSubmit={handleSignup}>
                <Form.Group className="mb-3">
                  <Form.Label>OTP</Form.Label>
                  <Form.Control name="otp" placeholder="000000" required pattern="[0-9]{6}" onInvalid={()=>setError("Please Enter 6 digits OTP")} value={otp} onChange={(e)=>setOtp(e.target.value)} />
                </Form.Group>
                <Button type="submit" variant="primary" className="w-100 fw-semibold" disabled={loading}>
                  {loading ? 'Creating account...' : 'Submit OTP'}
                </Button>
              </Form>
            </Card.Body>
          </Card>
        </Container>
      }
    </div>
  );
};

export default Signup;
