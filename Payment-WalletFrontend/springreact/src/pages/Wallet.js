import React, { useEffect, useState } from 'react';
import { Card, Row, Col, Button, Modal, Form, Alert } from 'react-bootstrap';
import Layout from '../components/Layout';
import Loader from '../components/Loader';

import {
  getWallet,
  getBankAccounts,
  topUpFromBank
} from '../services/walletService';

const Wallet = () => {
  const [wallet, setWallet] = useState(null);
  const [accounts, setAccounts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [bankLoading, setBankLoading] = useState(false);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [showTopUpAccount, setShowTopUpAccount] = useState(false);

  const [transferModal, setTransferModal] = useState(null);
  const [transferAmount, setTransferAmount] = useState('');
  const [submitting, setSubmitting] = useState(false);

  /* ---------- WALLET ---------- */
  const fetchWallet = async () => {
    try {
      const res = await getWallet();
      setWallet(res.data);
      setError('');
    } catch {
      setError('Failed to load wallet');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchWallet();
  }, []);

  /* ---------- BANK ACCOUNTS ---------- */
  const fetchBankAccounts = async () => {
    setBankLoading(true);
    try {
      const res = await getBankAccounts();
      setAccounts(res.data);
      setError('');
    } catch {
      setError('Failed to load bank accounts');
    } finally {
      setBankLoading(false);
    }
  };

  const showBankAccounts = () => {
    setShowTopUpAccount(true);
    fetchBankAccounts();
  };

  /* ---------- TRANSFER ---------- */
  const handleTransfer = async () => {
    const amt = Number(transferAmount);

    if (!amt || amt <= 0) {
      setError('Enter valid amount');
      return;
    }

    setSubmitting(true);
    setError('');
    setSuccess('');

    try {
      const res = await topUpFromBank(
      transferModal.bankAccountId,
        amt
      );

      setSuccess(res.data?.message || 'Amount transferred successfully');
      setTransferModal(null);
      setTransferAmount('');
      fetchWallet();
    } catch {
      setError('Transfer failed');
    } finally {
      setSubmitting(false);
    }
  };

  if (loading) return <Layout><Loader /></Layout>;

  return (
    <Layout>
      <h4 className="fw-bold mb-4">My Wallet</h4>

      {error && <Alert variant="danger" onClose={() => setError('')} dismissible>{error}</Alert>}
      {success && <Alert variant="success" onClose={() => setSuccess('')} dismissible>{success}</Alert>}

      <Row className="g-4">
        {/* LEFT */}
        <Col md={6}>
          <Card className="bg-primary text-white rounded-4 shadow">
            <Card.Body>
              <p className="small opacity-75">Available Balance</p>
              <h1 className="fw-bold">
                ₹{wallet?.balance?.toFixed(2) ?? '0.00'}
              </h1>

              <Button variant="light" className="mt-3" onClick={showBankAccounts}>
                Top Up
              </Button>
            </Card.Body>
          </Card>

          <Card className="mt-4 shadow-sm rounded-4">
            <Card.Header className="fw-bold">Linked Beneficiaries</Card.Header>

            {wallet?.beneficiary?.length === 0 ? (
              <Card.Body className="text-center text-muted">
                No beneficiaries linked yet
              </Card.Body>
            ) : (
              <ul className="list-group list-group-flush">
                {wallet?.beneficiary?.map(b => (
                  <li
                    key={b.beneficiaryId}
                    className="list-group-item d-flex align-items-center gap-3"
                  >
                    <div
                      className="rounded-circle bg-primary text-white fw-bold d-flex align-items-center justify-content-center"
                      style={{ width: 35, height: 35 }}
                    >
                      {b.beneficiaryName?.charAt(0)}
                    </div>
                    <div>
                      <div className="fw-semibold">{b.beneficiaryName}</div>
                      <small>{b.mobileNumber}</small>
                    </div>
                  </li>
                ))}
              </ul>
            )}
          </Card>
        </Col>

        {/* RIGHT */}
        <Col md={6}>
          {showTopUpAccount && (
            <>
              {bankLoading ? (
                <Loader />
              ) : accounts.length === 0 ? (
                <Card className="p-5 text-center text-muted">
                  No bank accounts linked yet
                </Card>
              ) : (
                <Row className="g-3">
                  {accounts.map(acc => (
                    <Col md={6} key={acc.bankAccountId}>
                      <Card className="rounded-4 shadow-sm">
                        <Card.Body>
                          <h6 className="fw-bold">{acc.bankname}</h6>
                          <small>{acc.accountNo}</small>
                          <Button
                            className="mt-3 w-100"
                            onClick={() => setTransferModal(acc)}
                          >
                            Select
                          </Button>
                        </Card.Body>
                      </Card>
                    </Col>
                  ))}
                </Row>
              )}
            </>
          )}
        </Col>
      </Row>

      {/* TRANSFER MODAL */}
      <Modal show={!!transferModal} onHide={() => setTransferModal(null)} centered>
        <Modal.Header closeButton>
          <Modal.Title>Transfer to Wallet</Modal.Title>
        </Modal.Header>

        <Modal.Body>
          <p className="text-muted">{transferModal?.bankname}</p>
          <Form.Control
            type="number"
            min="1"
            value={transferAmount}
            onChange={e => setTransferAmount(e.target.value)}
            placeholder="Enter amount"
          />
        </Modal.Body>

        <Modal.Footer>
          <Button variant="secondary" onClick={() => setTransferModal(null)}>
            Cancel
          </Button>
          <Button onClick={handleTransfer} disabled={submitting}>
            {submitting ? 'Transferring...' : 'Transfer'}
          </Button>
        </Modal.Footer>
      </Modal>
    </Layout>
  );
};

export default Wallet;