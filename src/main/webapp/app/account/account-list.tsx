import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError } from 'app/common/utils';
import { AccountDTO } from 'app/account/account-model';
import axios from 'axios';
import useDocumentTitle from 'app/common/use-document-title';


export default function AccountList() {
  const { t } = useTranslation();
  useDocumentTitle(t('account.list.headline'));

  const [accounts, setAccounts] = useState<AccountDTO[]>([]);
  const navigate = useNavigate();

  const getAllAccounts = async () => {
    try {
      const response = await axios.get('/api/accounts');
      setAccounts(response.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  const confirmDelete = async (id: number) => {
    if (!confirm(t('delete.confirm'))) {
      return;
    }
    try {
      await axios.delete('/api/accounts/' + id);
      navigate('/accounts', {
            state: {
              msgInfo: t('account.delete.success')
            }
          });
      getAllAccounts();
    } catch (error: any) {
      if (error?.response?.data?.code === 'REFERENCED') {
        const messageParts = error.response.data.message.split(',');
        navigate('/accounts', {
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
    getAllAccounts();
  }, []);

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('account.list.headline')}</h1>
      <div>
        <Link to="/accounts/add" className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2">{t('account.list.createNew')}</Link>
      </div>
    </div>
    {!accounts || accounts.length === 0 ? (
    <div>{t('account.list.empty')}</div>
    ) : (
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr>
            <th scope="col" className="text-left p-2">{t('account.id.label')}</th>
            <th scope="col" className="text-left p-2">{t('account.accountNumber.label')}</th>
            <th scope="col" className="text-left p-2">{t('account.accountType.label')}</th>
            <th scope="col" className="text-left p-2">{t('account.balance.label')}</th>
            <th scope="col" className="text-left p-2">{t('account.currency.label')}</th>
            <th scope="col" className="text-left p-2">{t('account.createdAt.label')}</th>
            <th scope="col" className="text-left p-2">{t('account.user.label')}</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="border-t-2 border-black">
          {accounts.map((account) => (
          <tr key={account.id} className="odd:bg-gray-100">
            <td className="p-2">{account.id}</td>
            <td className="p-2">{account.accountNumber}</td>
            <td className="p-2">{account.accountType}</td>
            <td className="p-2">{account.balance}</td>
            <td className="p-2">{account.currency}</td>
            <td className="p-2">{account.createdAt}</td>
            <td className="p-2">{account.user}</td>
            <td className="p-2">
              <div className="float-right whitespace-nowrap">
                <Link to={'/accounts/edit/' + account.id} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('account.list.edit')}</Link>
                <span> </span>
                <button type="button" onClick={() => confirmDelete(account.id!)} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm cursor-pointer">{t('account.list.delete')}</button>
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
