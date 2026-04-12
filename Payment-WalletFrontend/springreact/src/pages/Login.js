import React, { useState } from 'react';
import { Container, Card, Form, Button, Alert } from 'react-bootstrap';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';

const Login = () => {
  const navigate = useNavigate();
  const { login, handleSendOtp, forgetPassword } = useAuth();
  const [form, setForm] = useState({ email: '', pwd: '' });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const [forgetPasswordForm, setForgetPasswordForm] = useState({
    email: "",
    otp: "",
    newPwd: ""
  });
  const [showPasswordForm, setShowPasswordForm] = useState(false);
  const [otpSent, setOtpSent] = useState(false);

  const handleChange = (e) =>{
    setForm({ ...form, [e.target.name]: e.target.value });
    setError("")
  }

  const handleChangeForgetPasswordForm = (e) =>{
    setForgetPasswordForm({ ...forgetPasswordForm, [e.target.name]: e.target.value });
    setError("")
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    if (!form.email || !form.pwd) {
      setError('All fields are required.');
      return;
    }
    setLoading(true);
    try {
      await login(form.email, form.pwd);
      navigate('/dashboard');
    } catch (err) {
      setError(err.response?.data?.message || 'Login failed. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  const sendOtp = async (e) => {
    e.preventDefault();
    setError('');
    console.log(forgetPasswordForm);
    if (!forgetPasswordForm.email) {
      setError('email is required.');
      return;
    }
    if(!/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(forgetPasswordForm.email)){
      setError('email should be valid');
      return;
    }
    try {
      setLoading(true);
      await handleSendOtp({ email: forgetPasswordForm.email });
      setOtpSent(true);
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to send OTP. Please try again.');
    } finally {
      setLoading(false);
    }
  }

  const handleForgetPassword = async (e) => {
    e.preventDefault();
    setError('');
    console.log(forgetPasswordForm);
    if (!forgetPasswordForm.email || !forgetPasswordForm.newPwd || !forgetPasswordForm.otp) {
      setError('All fields are required.');
      return;
    }
    setLoading(true);
    try {
      await forgetPassword({ email: forgetPasswordForm.email, newPwd: forgetPasswordForm.newPwd, otp: forgetPasswordForm.otp });
      navigate('/dashboard');
    } catch (err) {
      setError(err.response?.data?.message || 'Invalid OTP. Please try again.');
    } finally {
      setLoading(false);
    }
  };



  return (
    <div
      className="d-flex align-items-center justify-content-center bg-light"
      style={{ minHeight: '100vh' }}
    >
      {showPasswordForm === false ?
        <Container style={{ maxWidth: '420px' }}>
          <div className="text-center mb-4">
            <i className="bi bi-wallet2 text-primary" style={{ fontSize: '2.5rem' }}></i>
            <h3 className="fw-bold text-primary mt-2">PayWallet</h3>
            <p className="text-muted">Sign in to your account</p>
          </div>
          <Card className="shadow border-0 rounded-4">
            <Card.Body className="p-4">
              {error && <Alert variant="danger">{error}</Alert>}
              <Form onSubmit={handleSubmit}>
                <Form.Group className="mb-3">
                  <Form.Label>Email address</Form.Label>
                  <Form.Control
                    type="email"
                    name="email"
                    placeholder="Enter email"
                    value={form.email}
                    onChange={handleChange}
                    required
                    pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}"
                    onInvalid={()=>setError("Please enter valid email")}
                  />
                </Form.Group>
                <Form.Group className="mb-4">
                  <Form.Label>Password</Form.Label>
                  <Form.Control
                    type="password"
                    name="pwd"
                    placeholder="Enter password"
                    value={form.pwd}
                    onChange={handleChange}
                    required
                    pattern="(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@.#$!%*?&])[A-Za-z\d@.#$!%*?&]{8,15}"
                    onInvalid={()=>setError("Password should have atleast 1 uppercase, 1 lowercase, 1 digit and 1 special character")}
                  />
                </Form.Group>
                <button type='button' onClick={() => setShowPasswordForm(true)} className=" border-0 bg-transparent mb-2 text-primary fs-6 fw-semibold">
                  Forget Password?{' '}
                </button>

                <Button
                  type="submit"
                  variant="primary"
                  className="w-100 fw-semibold"
                  disabled={loading}
                >
                  {loading ? 'Signing in...' : 'Sign In'}
                </Button>
              </Form>
              <p className="text-center mt-3 mb-0 text-muted">
                Don't have an account?{' '}
                <Link to="/signup" className="text-primary fw-semibold">
                  Sign Up
                </Link>
              </p>
            </Card.Body>
          </Card>
        </Container>

        : <Container style={{ maxWidth: '420px' }}>
          <div className="text-center mb-4">
            <i className="bi bi-wallet2 text-primary" style={{ fontSize: '2.5rem' }}></i>
            <h3 className="fw-bold text-primary mt-2">PayWallet</h3>
            <p className="text-muted">Forget Password</p>
          </div>

          <Card className="shadow border-0 rounded-4">
            <Card.Body className="p-4">
              {error && <Alert variant="danger">{error}</Alert>}
              <button type='button' onClick={() => setShowPasswordForm(false)} className=" border-0 bg-transparent mb-2 text-primary fs-6 fw-semibold">
                Back to login{' '}
              </button>
              <Form onSubmit={handleForgetPassword}>
                <Form.Group className="mb-3">
                  <Form.Label>Email address</Form.Label>
                  <Form.Control
                    type="email"
                    name="email"
                    placeholder="Enter email"
                    value={forgetPasswordForm.email}
                    onChange={handleChangeForgetPasswordForm}
                    required
                    pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}"
                    onInvalid={()=>setError("Please enter valid email")}
                  />
                </Form.Group>

                {otpSent ?
                  <>
                    <Form.Group className="mb-3">
                      <Form.Label>OTP</Form.Label>
                      <Form.Control name="otp" value={forgetPasswordForm.otp} placeholder="000000" required pattern="[0-9]{6}" onInvalid={()=>setError("Please Enter 6 digits OTP")} onChange={handleChangeForgetPasswordForm} />
                    </Form.Group>

                    <Form.Group className="mb-4">
                      <Form.Label>New Password</Form.Label>
                      <Form.Control
                        type="password"
                        name="newPwd"
                        placeholder="Enter password"
                        value={forgetPasswordForm.newPwd}
                        onChange={handleChangeForgetPasswordForm}
                        required
                        pattern="(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@.#$!%*?&])[A-Za-z\d@.#$!%*?&]{8,15}"
                        onInvalid={()=>setError("Password should have atleast 1 uppercase, 1 lowercase, 1 digit and 1 special character")}
                      />
                    </Form.Group>

                    <Button
                      type="submit"
                      variant="primary"
                      className="w-100 fw-semibold"
                      disabled={loading}
                    >
                      {loading ? 'Verifying...' : 'Forget Password'}
                    </Button></>
                  : <Button
                    type="button"
                    variant="primary"
                    className="w-100 fw-semibold"
                    onClick={sendOtp}
                    disabled={loading}
                  >
                    {loading ? 'Sending otp...' : 'Send Otp'}
                  </Button>}
              </Form>
            </Card.Body>
          </Card>
        </Container>}
    </div>
  );
};

export default Login;
