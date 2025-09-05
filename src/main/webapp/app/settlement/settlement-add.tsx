import React from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError, setYupDefaults } from 'app/common/utils';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { SettlementDTO } from 'app/settlement/settlement-model';
import axios from 'axios';
import InputRow from 'app/common/input-row/input-row';
import useDocumentTitle from 'app/common/use-document-title';
import * as yup from 'yup';


function getSchema() {
  setYupDefaults();
  return yup.object({
    date: yup.string().emptyToNull().required(),
    totalTransactions: yup.number().integer().emptyToNull(),
    totalAmount: yup.string().emptyToNull().numeric(15, 2),
    status: yup.string().emptyToNull().max(255),
    generatedAt: yup.string().emptyToNull().offsetDateTime()
  });
}

export default function SettlementAdd() {
  const { t } = useTranslation();
  useDocumentTitle(t('settlement.add.headline'));

  const navigate = useNavigate();

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const createSettlement = async (data: SettlementDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.post('/api/settlements', data);
      navigate('/settlements', {
            state: {
              msgSuccess: t('settlement.create.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('settlement.add.headline')}</h1>
      <div>
        <Link to="/settlements" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('settlement.add.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(createSettlement)} noValidate>
      <InputRow useFormResult={useFormResult} object="settlement" field="date" required={true} type="datepicker" />
      <InputRow useFormResult={useFormResult} object="settlement" field="totalTransactions" type="number" />
      <InputRow useFormResult={useFormResult} object="settlement" field="totalAmount" />
      <InputRow useFormResult={useFormResult} object="settlement" field="status" />
      <InputRow useFormResult={useFormResult} object="settlement" field="generatedAt" />
      <input type="submit" value={t('settlement.add.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 cursor-pointer mt-6" />
    </form>
  </>);
}
