import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError, setYupDefaults } from 'app/common/utils';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { AccountDTO } from 'app/account/account-model';
import axios from 'axios';
import InputRow from 'app/common/input-row/input-row';
import useDocumentTitle from 'app/common/use-document-title';
import * as yup from 'yup';


function getSchema() {
  setYupDefaults();
  return yup.object({
    accountNumber: yup.string().emptyToNull().max(20).required(),
    accountType: yup.string().emptyToNull().max(255).required(),
    balance: yup.string().emptyToNull().numeric(15, 2),
    currency: yup.string().emptyToNull().max(3),
    createdAt: yup.string().emptyToNull().offsetDateTime(),
    user: yup.number().integer().emptyToNull().required()
  });
}

export default function AccountAdd() {
  const { t } = useTranslation();
  useDocumentTitle(t('account.add.headline'));

  const navigate = useNavigate();
  const [userValues, setUserValues] = useState<Map<number,string>>(new Map());

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const prepareRelations = async () => {
    try {
      const userValuesResponse = await axios.get('/api/accounts/userValues');
      setUserValues(userValuesResponse.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareRelations();
  }, []);

  const createAccount = async (data: AccountDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.post('/api/accounts', data);
      navigate('/accounts', {
            state: {
              msgSuccess: t('account.create.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('account.add.headline')}</h1>
      <div>
        <Link to="/accounts" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('account.add.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(createAccount)} noValidate>
      <InputRow useFormResult={useFormResult} object="account" field="accountNumber" required={true} />
      <InputRow useFormResult={useFormResult} object="account" field="accountType" required={true} />
      <InputRow useFormResult={useFormResult} object="account" field="balance" />
      <InputRow useFormResult={useFormResult} object="account" field="currency" />
      <InputRow useFormResult={useFormResult} object="account" field="createdAt" />
      <InputRow useFormResult={useFormResult} object="account" field="user" required={true} type="select" options={userValues} />
      <input type="submit" value={t('account.add.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 cursor-pointer mt-6" />
    </form>
  </>);
}
