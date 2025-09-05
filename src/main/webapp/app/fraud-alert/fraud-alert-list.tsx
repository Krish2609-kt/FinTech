import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError } from 'app/common/utils';
import { FraudAlertDTO } from 'app/fraud-alert/fraud-alert-model';
import axios from 'axios';
import useDocumentTitle from 'app/common/use-document-title';


export default function FraudAlertList() {
  const { t } = useTranslation();
  useDocumentTitle(t('fraudAlert.list.headline'));

  const [fraudAlerts, setFraudAlerts] = useState<FraudAlertDTO[]>([]);
  const navigate = useNavigate();

  const getAllFraudAlerts = async () => {
    try {
      const response = await axios.get('/api/fraudAlerts');
      setFraudAlerts(response.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  const confirmDelete = async (id: number) => {
    if (!confirm(t('delete.confirm'))) {
      return;
    }
    try {
      await axios.delete('/api/fraudAlerts/' + id);
      navigate('/fraudAlerts', {
            state: {
              msgInfo: t('fraudAlert.delete.success')
            }
          });
      getAllFraudAlerts();
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    getAllFraudAlerts();
  }, []);

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('fraudAlert.list.headline')}</h1>
      <div>
        <Link to="/fraudAlerts/add" className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2">{t('fraudAlert.list.createNew')}</Link>
      </div>
    </div>
    {!fraudAlerts || fraudAlerts.length === 0 ? (
    <div>{t('fraudAlert.list.empty')}</div>
    ) : (
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr>
            <th scope="col" className="text-left p-2">{t('fraudAlert.id.label')}</th>
            <th scope="col" className="text-left p-2">{t('fraudAlert.alertType.label')}</th>
            <th scope="col" className="text-left p-2">{t('fraudAlert.status.label')}</th>
            <th scope="col" className="text-left p-2">{t('fraudAlert.createdAt.label')}</th>
            <th scope="col" className="text-left p-2">{t('fraudAlert.transaction.label')}</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="border-t-2 border-black">
          {fraudAlerts.map((fraudAlert) => (
          <tr key={fraudAlert.id} className="odd:bg-gray-100">
            <td className="p-2">{fraudAlert.id}</td>
            <td className="p-2">{fraudAlert.alertType}</td>
            <td className="p-2">{fraudAlert.status}</td>
            <td className="p-2">{fraudAlert.createdAt}</td>
            <td className="p-2">{fraudAlert.transaction}</td>
            <td className="p-2">
              <div className="float-right whitespace-nowrap">
                <Link to={'/fraudAlerts/edit/' + fraudAlert.id} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('fraudAlert.list.edit')}</Link>
                <span> </span>
                <button type="button" onClick={() => confirmDelete(fraudAlert.id!)} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm cursor-pointer">{t('fraudAlert.list.delete')}</button>
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
