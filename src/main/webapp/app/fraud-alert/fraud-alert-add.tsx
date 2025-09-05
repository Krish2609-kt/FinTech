import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError, setYupDefaults } from 'app/common/utils';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { FraudAlertDTO } from 'app/fraud-alert/fraud-alert-model';
import axios from 'axios';
import InputRow from 'app/common/input-row/input-row';
import useDocumentTitle from 'app/common/use-document-title';
import * as yup from 'yup';


function getSchema() {
  setYupDefaults();
  return yup.object({
    alertType: yup.string().emptyToNull().max(50).required(),
    details: yup.string().emptyToNull(),
    status: yup.string().emptyToNull().max(255),
    createdAt: yup.string().emptyToNull().offsetDateTime(),
    transaction: yup.number().integer().emptyToNull().required()
  });
}

export default function FraudAlertAdd() {
  const { t } = useTranslation();
  useDocumentTitle(t('fraudAlert.add.headline'));

  const navigate = useNavigate();
  const [transactionValues, setTransactionValues] = useState<Map<number,string>>(new Map());

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const prepareRelations = async () => {
    try {
      const transactionValuesResponse = await axios.get('/api/fraudAlerts/transactionValues');
      setTransactionValues(transactionValuesResponse.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareRelations();
  }, []);

  const createFraudAlert = async (data: FraudAlertDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.post('/api/fraudAlerts', data);
      navigate('/fraudAlerts', {
            state: {
              msgSuccess: t('fraudAlert.create.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('fraudAlert.add.headline')}</h1>
      <div>
        <Link to="/fraudAlerts" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('fraudAlert.add.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(createFraudAlert)} noValidate>
      <InputRow useFormResult={useFormResult} object="fraudAlert" field="alertType" required={true} />
      <InputRow useFormResult={useFormResult} object="fraudAlert" field="details" type="textarea" />
      <InputRow useFormResult={useFormResult} object="fraudAlert" field="status" />
      <InputRow useFormResult={useFormResult} object="fraudAlert" field="createdAt" />
      <InputRow useFormResult={useFormResult} object="fraudAlert" field="transaction" required={true} type="select" options={transactionValues} />
      <input type="submit" value={t('fraudAlert.add.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 cursor-pointer mt-6" />
    </form>
  </>);
}
