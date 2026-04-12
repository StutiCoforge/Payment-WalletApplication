import React, { useEffect, useState } from 'react';
import { Card, Button, Alert, Modal, Form, Table } from 'react-bootstrap';
import Layout from '../components/Layout';
import Loader from '../components/Loader';
import { getBankAccounts, addBankAccount, transferToWallet, deleteBankAccount } from '../services/bankService';

const BankAccounts = () => {
  const [accounts, setAccounts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [addFormError, setAddFormError] = useState('');
  const [success, setSuccess] = useState('');
  const [addModal, setAddModal] = useState(false);
  const [transferModal, setTransferModal] = useState({ show: false, accountId: null });
  const [form, setForm] = useState({ accountNo: '', bankname: '', ifscCode: '' });
  const [transferAmount, setTransferAmount] = useState('');
  const [submitting, setSubmitting] = useState(false);

  const fetchAccounts = async () => {
    try {
      const res = await getBankAccounts();
      setAccounts(res.data || []);
    } catch { setError('Failed to load bank accounts.'); }
    finally { setLoading(false); }
  };

  useEffect(() => { fetchAccounts(); }, []);
  const handleDelete = async (bankAccountId) => {
    const confirm = window.confirm('Are you sure you want to delete this bank account?');
    if (!confirm) return;

    setSubmitting(true);
    setError('');
    setSuccess('');

    try {
      const res = await deleteBankAccount(bankAccountId);
      setSuccess(res.data?.message || 'Bank account deleted');
      fetchAccounts();
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to delete bank account.');
    } finally {
      setSubmitting(false);
    }
  };
  const handleAdd = async () => {
    if (!form.accountNo || !form.bankname) { setError('Account number and bank name are required.'); return; }
    setSubmitting(true);
    try {
      await addBankAccount(form);
      setSuccess('Bank account added.');
      setAddModal(false);
      setForm({ accountNo: '  ', bankname: '', ifscCode: '' });
      fetchAccounts();
    } catch (err) { setAddFormError(err.response?.data?.message || 'Failed to add account.'); }
    finally { setSubmitting(false); }
  };

  const handleTransfer = async () => {
    if (!transferAmount || isNaN(transferAmount) || Number(transferAmount) <= 0) {
      setError('Enter a valid amount.');
      return;
    }
    setSubmitting(true);
    try {
      await transferToWallet(transferModal.accountId, Number(transferAmount));
      setSuccess('Transfer to wallet successful.');
      setTransferModal({ show: false, accountId: null });
      setTransferAmount('');
    } catch (err) { setError(err.response?.data?.message || 'Transfer failed.'); }
    finally { setSubmitting(false); }
  };

  if (loading) return <Layout><Loader /></Layout>;

  return (
    <Layout>
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h4 className="fw-bold text-primary mb-0">Bank Accounts</h4>
        <Button variant="primary" onClick={() => setAddModal(true)}>
          <i className="bi bi-plus-lg me-2"></i>Add Account
        </Button>
      </div>
      {error && <Alert variant="danger" dismissible onClose={() => setError('')}>{error}</Alert>}
      {success && <Alert variant="success" dismissible onClose={() => setSuccess('')}>{success}</Alert>}

      <Card className="border-0 shadow-sm rounded-4">
        <Card.Body className="p-0">
          <Table hover responsive className="mb-0">
            <thead className="table-light">
              <tr><th>#</th><th>Account No.</th><th>Bank</th><th>IFSC</th><th>Balance</th><th>Actions</th></tr>
            </thead>
            <tbody>
              {accounts.length === 0 ? (
                <tr><td colSpan={6} className="text-center text-muted py-4">No bank accounts found.</td></tr>
              ) : accounts.map((acc, i) => (
                <tr key={acc.bankAccountId || i}>
                  <td>{i + 1}</td>
                  <td>{acc.accountNo}</td>
                  <td>{acc.bankname}</td>
                  <td>{acc.ifscCode || '—'}</td>
                  <td>₹{acc.balance || '0.00'}</td>
                  <td>
                    <Button size="sm" variant="outline-primary" onClick={() => setTransferModal({ show: true, accountId: acc.bankAccountId })}>
                      Transfer to Wallet
                    </Button>
                  </td>
                  <td>

                    <Button
                      size="sm"
                      variant="outline-danger"
                      onClick={() => handleDelete(acc.bankAccountId)}
                    >
                      Delete
                    </Button>

                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </Card.Body>
      </Card>

      {/* Add Account Modal */}
      <Modal show={addModal} onHide={() => setAddModal(false)} centered>
        <Modal.Header closeButton><Modal.Title>Add Bank Account</Modal.Title></Modal.Header>
        <Modal.Body>
          {addFormError && <Alert variant="danger">{addFormError}</Alert>}
          <Form onSubmit={handleAdd}>
            <Form.Group className="mb-3">
              <Form.Label className="text-capitalize">Account No</Form.Label>
              <Form.Control value={form["accountNo"]} required pattern='[1-9][0-9]{9,}' onInvalid={() => setAddFormError("Please Enter Valid Account No")} onChange={(e) => setForm({ ...form, "accountNo": e.target.value })} />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label className="text-capitalize">Bank Name</Form.Label>
              <Form.Control value={form["bankname"]} required pattern='[A-Za-z]{3,}[A-Za-z\s]*' onInvalid={() => setAddFormError("Please Enter Valid Bank Name")} onChange={(e) => setForm({ ...form, "bankname": e.target.value })} />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label className="text-capitalize">IFSC Code</Form.Label>
              <Form.Control value={form["ifscCode"]} required pattern='[A-Z]{4}0[A-Z0-9]{6}' onInvalid={() => setAddFormError("Please Enter Valid IFSC Code")} onChange={(e) => setForm({ ...form, "ifscCode": e.target.value })} />
            </Form.Group>

            <Button type='button' variant="secondary" onClick={() => setAddModal(false)}>Cancel</Button>
            <Button variant="primary" className='mx-2' type='submit' disabled={submitting}>{submitting ? 'Adding...' : 'Add'}</Button>
          </Form>
        </Modal.Body>
      </Modal>

      {/* Transfer Modal */}
      <Modal show={transferModal.show} onHide={() => setTransferModal({ show: false, accountId: null })} centered>
        <Modal.Header closeButton><Modal.Title>Transfer to Wallet</Modal.Title></Modal.Header>
        <Modal.Body>
          {error && <Alert variant="danger" dismissible onClose={() => setError('')}>{error}</Alert>}
          <Form.Group>
            <Form.Label>Amount (₹)</Form.Label>
            <Form.Control type="number" min="1" value={transferAmount} onChange={(e) => setTransferAmount(e.target.value)} />
          </Form.Group>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setTransferModal({ show: false, accountId: null })}>Cancel</Button>
          <Button variant="primary" onClick={handleTransfer} disabled={submitting}>{submitting ? 'Transferring...' : 'Transfer'}</Button>
        </Modal.Footer>
      </Modal>
    </Layout>
  );
};

export default BankAccounts;
