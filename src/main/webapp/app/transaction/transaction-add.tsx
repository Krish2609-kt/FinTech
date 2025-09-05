import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError, setYupDefaults } from 'app/common/utils';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { TransactionDTO } from 'app/transaction/transaction-model';
import axios from 'axios';
import InputRow from 'app/common/input-row/input-row';
import useDocumentTitle from 'app/common/use-document-title';
import * as yup from 'yup';


function getSchema() {
  setYupDefaults();
  return yup.object({
    amount: yup.string().emptyToNull().numeric(15, 2).required(),
    currency: yup.string().emptyToNull().max(3),
    status: yup.string().emptyToNull().max(255),
    transactionType: yup.string().emptyToNull().max(255).required(),
    initiatedAt: yup.string().emptyToNull().offsetDateTime(),
    completedAt: yup.string().emptyToNull().offsetDateTime(),
    fromAccount: yup.number().integer().emptyToNull().required(),
    toAccount: yup.number().integer().emptyToNull().required()
  });
}

export default function TransactionAdd() {
  const { t } = useTranslation();
  useDocumentTitle(t('transaction.add.headline'));

  const navigate = useNavigate();
  const [fromAccountValues, setFromAccountValues] = useState<Map<number,string>>(new Map());
  const [toAccountValues, setToAccountValues] = useState<Map<number,string>>(new Map());

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const prepareRelations = async () => {
    try {
      const fromAccountValuesResponse = await axios.get('/api/transactions/fromAccountValues');
      setFromAccountValues(fromAccountValuesResponse.data);
      const toAccountValuesResponse = await axios.get('/api/transactions/toAccountValues');
      setToAccountValues(toAccountValuesResponse.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareRelations();
  }, []);

  const createTransaction = async (data: TransactionDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.post('/api/transactions', data);
      navigate('/transactions', {
            state: {
              msgSuccess: t('transaction.create.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('transaction.add.headline')}</h1>
      <div>
        <Link to="/transactions" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('transaction.add.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(createTransaction)} noValidate>
      <InputRow useFormResult={useFormResult} object="transaction" field="amount" required={true} />
      <InputRow useFormResult={useFormResult} object="transaction" field="currency" />
      <InputRow useFormResult={useFormResult} object="transaction" field="status" />
      <InputRow useFormResult={useFormResult} object="transaction" field="transactionType" required={true} />
      <InputRow useFormResult={useFormResult} object="transaction" field="initiatedAt" />
      <InputRow useFormResult={useFormResult} object="transaction" field="completedAt" />
      <InputRow useFormResult={useFormResult} object="transaction" field="fromAccount" required={true} type="select" options={fromAccountValues} />
      <InputRow useFormResult={useFormResult} object="transaction" field="toAccount" required={true} type="select" options={toAccountValues} />
      <input type="submit" value={t('transaction.add.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 cursor-pointer mt-6" />
    </form>
  </>);
}
