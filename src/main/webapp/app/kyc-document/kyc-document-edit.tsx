import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate, useParams } from 'react-router';
import { handleServerError, setYupDefaults } from 'app/common/utils';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { KycDocumentDTO } from 'app/kyc-document/kyc-document-model';
import axios from 'axios';
import InputRow from 'app/common/input-row/input-row';
import useDocumentTitle from 'app/common/use-document-title';
import * as yup from 'yup';


function getSchema() {
  setYupDefaults();
  return yup.object({
    docType: yup.string().emptyToNull().max(255).required(),
    docNumber: yup.string().emptyToNull().max(50).required(),
    docFilePath: yup.string().emptyToNull().max(255),
    verified: yup.bool(),
    uploadedAt: yup.string().emptyToNull().offsetDateTime(),
    user: yup.number().integer().emptyToNull().required()
  });
}

export default function KycDocumentEdit() {
  const { t } = useTranslation();
  useDocumentTitle(t('kycDocument.edit.headline'));

  const navigate = useNavigate();
  const [userValues, setUserValues] = useState<Map<number,string>>(new Map());
  const params = useParams();
  const currentId = +params.id!;

  const useFormResult = useForm({
    resolver: yupResolver(getSchema()),
  });

  const prepareForm = async () => {
    try {
      const userValuesResponse = await axios.get('/api/kycDocuments/userValues');
      setUserValues(userValuesResponse.data);
      const data = (await axios.get('/api/kycDocuments/' + currentId)).data;
      useFormResult.reset(data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    prepareForm();
  }, []);

  const updateKycDocument = async (data: KycDocumentDTO) => {
    window.scrollTo(0, 0);
    try {
      await axios.put('/api/kycDocuments/' + currentId, data);
      navigate('/kycDocuments', {
            state: {
              msgSuccess: t('kycDocument.update.success')
            }
          });
    } catch (error: any) {
      handleServerError(error, navigate, useFormResult.setError, t);
    }
  };

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('kycDocument.edit.headline')}</h1>
      <div>
        <Link to="/kycDocuments" className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-4 rounded px-5 py-2">{t('kycDocument.edit.back')}</Link>
      </div>
    </div>
    <form onSubmit={useFormResult.handleSubmit(updateKycDocument)} noValidate>
      <InputRow useFormResult={useFormResult} object="kycDocument" field="id" disabled={true} type="number" />
      <InputRow useFormResult={useFormResult} object="kycDocument" field="docType" required={true} />
      <InputRow useFormResult={useFormResult} object="kycDocument" field="docNumber" required={true} />
      <InputRow useFormResult={useFormResult} object="kycDocument" field="docFilePath" />
      <InputRow useFormResult={useFormResult} object="kycDocument" field="verified" type="checkbox" />
      <InputRow useFormResult={useFormResult} object="kycDocument" field="uploadedAt" />
      <InputRow useFormResult={useFormResult} object="kycDocument" field="user" required={true} type="select" options={userValues} />
      <input type="submit" value={t('kycDocument.edit.headline')} className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2 cursor-pointer mt-6" />
    </form>
  </>);
}
