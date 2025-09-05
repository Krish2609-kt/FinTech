import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError } from 'app/common/utils';
import { KycDocumentDTO } from 'app/kyc-document/kyc-document-model';
import axios from 'axios';
import useDocumentTitle from 'app/common/use-document-title';


export default function KycDocumentList() {
  const { t } = useTranslation();
  useDocumentTitle(t('kycDocument.list.headline'));

  const [kycDocuments, setKycDocuments] = useState<KycDocumentDTO[]>([]);
  const navigate = useNavigate();

  const getAllKycDocuments = async () => {
    try {
      const response = await axios.get('/api/kycDocuments');
      setKycDocuments(response.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  const confirmDelete = async (id: number) => {
    if (!confirm(t('delete.confirm'))) {
      return;
    }
    try {
      await axios.delete('/api/kycDocuments/' + id);
      navigate('/kycDocuments', {
            state: {
              msgInfo: t('kycDocument.delete.success')
            }
          });
      getAllKycDocuments();
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    getAllKycDocuments();
  }, []);

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('kycDocument.list.headline')}</h1>
      <div>
        <Link to="/kycDocuments/add" className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2">{t('kycDocument.list.createNew')}</Link>
      </div>
    </div>
    {!kycDocuments || kycDocuments.length === 0 ? (
    <div>{t('kycDocument.list.empty')}</div>
    ) : (
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr>
            <th scope="col" className="text-left p-2">{t('kycDocument.id.label')}</th>
            <th scope="col" className="text-left p-2">{t('kycDocument.docType.label')}</th>
            <th scope="col" className="text-left p-2">{t('kycDocument.docNumber.label')}</th>
            <th scope="col" className="text-left p-2">{t('kycDocument.docFilePath.label')}</th>
            <th scope="col" className="text-left p-2">{t('kycDocument.verified.label')}</th>
            <th scope="col" className="text-left p-2">{t('kycDocument.uploadedAt.label')}</th>
            <th scope="col" className="text-left p-2">{t('kycDocument.user.label')}</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="border-t-2 border-black">
          {kycDocuments.map((kycDocument) => (
          <tr key={kycDocument.id} className="odd:bg-gray-100">
            <td className="p-2">{kycDocument.id}</td>
            <td className="p-2">{kycDocument.docType}</td>
            <td className="p-2">{kycDocument.docNumber}</td>
            <td className="p-2">{kycDocument.docFilePath}</td>
            <td className="p-2">{kycDocument.verified?.toString()}</td>
            <td className="p-2">{kycDocument.uploadedAt}</td>
            <td className="p-2">{kycDocument.user}</td>
            <td className="p-2">
              <div className="float-right whitespace-nowrap">
                <Link to={'/kycDocuments/edit/' + kycDocument.id} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('kycDocument.list.edit')}</Link>
                <span> </span>
                <button type="button" onClick={() => confirmDelete(kycDocument.id!)} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm cursor-pointer">{t('kycDocument.list.delete')}</button>
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
