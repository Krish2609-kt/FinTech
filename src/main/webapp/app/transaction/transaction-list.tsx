import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError } from 'app/common/utils';
import { TransactionDTO } from 'app/transaction/transaction-model';
import axios from 'axios';
import useDocumentTitle from 'app/common/use-document-title';


export default function TransactionList() {
  const { t } = useTranslation();
  useDocumentTitle(t('transaction.list.headline'));

  const [transactions, setTransactions] = useState<TransactionDTO[]>([]);
  const navigate = useNavigate();

  const getAllTransactions = async () => {
    try {
      const response = await axios.get('/api/transactions');
      setTransactions(response.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  const confirmDelete = async (id: number) => {
    if (!confirm(t('delete.confirm'))) {
      return;
    }
    try {
      await axios.delete('/api/transactions/' + id);
      navigate('/transactions', {
            state: {
              msgInfo: t('transaction.delete.success')
            }
          });
      getAllTransactions();
    } catch (error: any) {
      if (error?.response?.data?.code === 'REFERENCED') {
        const messageParts = error.response.data.message.split(',');
        navigate('/transactions', {
              state: {
                msgError: t(messageParts[0]!, { id: messageParts[1]! })
              }
            });
        return;
      }
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    getAllTransactions();
  }, []);

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('transaction.list.headline')}</h1>
      <div>
        <Link to="/transactions/add" className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2">{t('transaction.list.createNew')}</Link>
      </div>
    </div>
    {!transactions || transactions.length === 0 ? (
    <div>{t('transaction.list.empty')}</div>
    ) : (
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr>
            <th scope="col" className="text-left p-2">{t('transaction.id.label')}</th>
            <th scope="col" className="text-left p-2">{t('transaction.amount.label')}</th>
            <th scope="col" className="text-left p-2">{t('transaction.currency.label')}</th>
            <th scope="col" className="text-left p-2">{t('transaction.status.label')}</th>
            <th scope="col" className="text-left p-2">{t('transaction.transactionType.label')}</th>
            <th scope="col" className="text-left p-2">{t('transaction.initiatedAt.label')}</th>
            <th scope="col" className="text-left p-2">{t('transaction.completedAt.label')}</th>
            <th scope="col" className="text-left p-2">{t('transaction.fromAccount.label')}</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="border-t-2 border-black">
          {transactions.map((transaction) => (
          <tr key={transaction.id} className="odd:bg-gray-100">
            <td className="p-2">{transaction.id}</td>
            <td className="p-2">{transaction.amount}</td>
            <td className="p-2">{transaction.currency}</td>
            <td className="p-2">{transaction.status}</td>
            <td className="p-2">{transaction.transactionType}</td>
            <td className="p-2">{transaction.initiatedAt}</td>
            <td className="p-2">{transaction.completedAt}</td>
            <td className="p-2">{transaction.fromAccount}</td>
            <td className="p-2">
              <div className="float-right whitespace-nowrap">
                <Link to={'/transactions/edit/' + transaction.id} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('transaction.list.edit')}</Link>
                <span> </span>
                <button type="button" onClick={() => confirmDelete(transaction.id!)} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm cursor-pointer">{t('transaction.list.delete')}</button>
              </div>
            </td>
          </tr>
          ))}
        </tbody>
      </table>
    </div>
    )}
  </>);
}
