import React, { useEffect, useState } from 'react';
import { Card, Button, Alert, Modal, Form, Table, Row, Col } from 'react-bootstrap';
import Layout from '../components/Layout';
import Loader from '../components/Loader';
import { payBill, getBillHistory } from '../services/billService';

const BILL_TYPES = ['Electricity', 'Gas Booking', 'Mobile Recharge'];
const MOBILE_OPERATORS = ['JIO', 'VI', 'AIRTEL', 'BSNL'];

const Bills = () => {
  const [history, setHistory] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [submitting, setSubmitting] = useState(false);

  const initialForm = {
    billType: '',
    amount: '',
    billData: {}
  };

  const [form, setForm] = useState(initialForm);

  const fetchHistory = async () => {
    try {
      const res = await getBillHistory();
      setHistory(res.data || []);
    } catch {
      setError('Failed to load bill history.');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchHistory();
  }, []);

  // ✅ same logic as Angular createBill()
  const handlePay = async () => {
    if (!form.billType || !form.amount) {
      setError('Bill type and amount are required.');
      return;
    }

    const payload = {
      billType: form.billType.toUpperCase().replace(' ', '_'),
      amount: Number(form.amount),
      billData: form.billData
    };

    setSubmitting(true);
    try {
      await payBill(payload);
      setSuccess('Bill paid successfully');
      setShowModal(false);
      setForm(initialForm); // ✅ clear form after pay
      fetchHistory();
    } catch (err) {
      setError('Payment failed.');
    } finally {
      setSubmitting(false);
    }
  };

  if (loading) {
    return (
      <Layout>
        <Loader />
      </Layout>
    );
  }

  return (
    <Layout>
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h4 className="fw-bold text-primary mb-0">Bill Payments</h4>
        <Button onClick={() => setShowModal(true)}>Pay Bill</Button>
      </div>

      {error && <Alert variant="danger">{error}</Alert>}
      {success && <Alert variant="success">{success}</Alert>}

      {/* Bill Type Cards */}
      <Row className="g-3 mb-4">
        {BILL_TYPES.map(type => (
          <Col md={4} key={type}>
            <Card
              className="p-3 text-center shadow-sm"
              onClick={() => {
                setForm({ ...initialForm, billType: type });
                setShowModal(true);
              }}
              style={{ cursor: 'pointer' }}
            >

              <div className="fw-semibold">{type}</div>
            </Card>
          </Col>
        ))}
      </Row>

      {/* History */}
      <Card className="shadow-sm">
        <Table hover responsive>
          <thead>
            <tr>
              <th>#</th>
              <th>Bill Type</th>
              <th>Amount</th>
              <th>Date</th>
            </tr>
          </thead>
          <tbody>
            {history.map((b, i) => (
              <tr key={b.billId}>
                <td>{i + 1}</td>
                <td>{b.billType}</td>
                <td>₹{b.amount}</td>
                <td>{b.paymentDate?.slice(0, 10)}</td>
              </tr>
            ))}
          </tbody>
        </Table>
      </Card>

      {/* ✅ Modal – fields just like Angular */}
      <Modal show={showModal} onHide={() => setShowModal(false)} centered>
        <Modal.Header closeButton>
          <Modal.Title>Pay Bill</Modal.Title>
        </Modal.Header>

        <Modal.Body>
          <Form onSubmit={handlePay}>
            {error && <Alert variant="danger">{error}</Alert>}
            <Form.Group className="mb-3">
              <Form.Label>Bill Type</Form.Label>
              <Form.Select
                value={form.billType}
                onChange={e => setForm({ ...form, billType: e.target.value, billData: {} })}
                required
                onInvalid={() => setError("Please select valid bill type")}
              >
                <option value="">Select</option>
                {BILL_TYPES.map(t => <option key={t}>{t}</option>)}

              </Form.Select>
            </Form.Group>

            {/* ELECTRICITY */}
            {form.billType === 'Electricity' && (
              <>
                <Form.Control className="mb-2" placeholder="State"
                  onChange={e => setForm({
                    ...form,
                    billData: { ...form.billData, state: e.target.value }
                  })}
                  required
                  name='state'
                  pattern="[A-Za-z]{3,}[A-Za-z\s]*"
                  onInvalid={() => setError("Please select valid state name")}
                />
                <Form.Control className="mb-2" placeholder="Biller Name"
                  onChange={e => setForm({
                    ...form,
                    billData: { ...form.billData, billerName: e.target.value }
                  })}
                  required
                  pattern="[a-zA-Z]{2}[a-zA-Z\s]{0,20}[a-zA-Z]"
                  onInvalid={() => setError("Please select valid biller name")}
                />
                <Form.Control placeholder="Account Number"
                  onChange={e => setForm({
                    ...form,
                    billData: { ...form.billData, accountNumber: e.target.value }
                  })}
                  required
                  pattern="[1-9][0-9]{8,}"
                  onInvalid={() => setError("Please select valid account number")}
                />
              </>
            )}

            {/* MOBILE RECHARGE */}
            {form.billType === 'Mobile Recharge' && (
              <>
                <Form.Control className="mb-2" placeholder="Mobile Number"
                  onChange={e => setForm({
                    ...form,
                    billData: { ...form.billData, mobileNumber: e.target.value }
                  })}
                  required
                  pattern="[6-9][0-9]{9}"
                  onInvalid={() => setError("Please select valid mobile number")}
                />
                <Form.Select
                  onChange={e => setForm({
                    ...form,
                    billData: { ...form.billData, operator: e.target.value }
                  })}
                  required
                  onInvalid={() => setError("Please select valid operator")}
                >
                  {MOBILE_OPERATORS.map(op => <option key={op}>{op}</option>)}
                </Form.Select>
              </>
            )}

            {/* GAS BOOKING */}
            {form.billType === 'Gas Booking' && (
              <>
                <Form.Control className="mb-2" placeholder="Gas Provider"
                  onChange={e => setForm({
                    ...form,
                    billData: { ...form.billData, gasProvider: e.target.value }
                  })}
                  required
                  pattern="[a-zA-Z][a-zA-Z][a-zA-Z\s]{0,20}[a-zA-Z]"
                  onInvalid={() => setError("Please select valid gas provider")}
                />
                <Form.Control placeholder="Customer Number"
                  onChange={e => setForm({
                    ...form,
                    billData: { ...form.billData, customerNumber: e.target.value }
                  })}
                  required
                  pattern="[1-9][0-9]{9,}"
                  onInvalid={() => setError("Please select valid customer number")}
                />
              </>
            )}

            <Form.Control
              className="mt-3"
              type="number"
              min="1"
              placeholder="Amount"
              value={form.amount}
              onChange={e => setForm({ ...form, amount: e.target.value })}
            />


            <Button variant="secondary" type='button' onClick={() => setShowModal(false)}>Cancel</Button>
            <Button className='m-2' type='submit' disabled={submitting}>
              {submitting ? 'Processing...' : 'Pay'}
            </Button>
          </Form>
        </Modal.Body>
      </Modal>
    </Layout>
  );
};

export default Bills;
