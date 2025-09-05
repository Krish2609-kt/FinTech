import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router';
import { handleServerError } from 'app/common/utils';
import { SettlementDTO } from 'app/settlement/settlement-model';
import axios from 'axios';
import useDocumentTitle from 'app/common/use-document-title';


export default function SettlementList() {
  const { t } = useTranslation();
  useDocumentTitle(t('settlement.list.headline'));

  const [settlements, setSettlements] = useState<SettlementDTO[]>([]);
  const navigate = useNavigate();

  const getAllSettlements = async () => {
    try {
      const response = await axios.get('/api/settlements');
      setSettlements(response.data);
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  const confirmDelete = async (id: number) => {
    if (!confirm(t('delete.confirm'))) {
      return;
    }
    try {
      await axios.delete('/api/settlements/' + id);
      navigate('/settlements', {
            state: {
              msgInfo: t('settlement.delete.success')
            }
          });
      getAllSettlements();
    } catch (error: any) {
      handleServerError(error, navigate);
    }
  };

  useEffect(() => {
    getAllSettlements();
  }, []);

  return (<>
    <div className="flex flex-wrap mb-6">
      <h1 className="grow text-3xl md:text-4xl font-medium mb-2">{t('settlement.list.headline')}</h1>
      <div>
        <Link to="/settlements/add" className="inline-block text-white bg-blue-600 hover:bg-blue-700 focus:ring-blue-300  focus:ring-4 rounded px-5 py-2">{t('settlement.list.createNew')}</Link>
      </div>
    </div>
    {!settlements || settlements.length === 0 ? (
    <div>{t('settlement.list.empty')}</div>
    ) : (
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr>
            <th scope="col" className="text-left p-2">{t('settlement.id.label')}</th>
            <th scope="col" className="text-left p-2">{t('settlement.date.label')}</th>
            <th scope="col" className="text-left p-2">{t('settlement.totalTransactions.label')}</th>
            <th scope="col" className="text-left p-2">{t('settlement.totalAmount.label')}</th>
            <th scope="col" className="text-left p-2">{t('settlement.status.label')}</th>
            <th scope="col" className="text-left p-2">{t('settlement.generatedAt.label')}</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="border-t-2 border-black">
          {settlements.map((settlement) => (
          <tr key={settlement.id} className="odd:bg-gray-100">
            <td className="p-2">{settlement.id}</td>
            <td className="p-2">{settlement.date}</td>
            <td className="p-2">{settlement.totalTransactions}</td>
            <td className="p-2">{settlement.totalAmount}</td>
            <td className="p-2">{settlement.status}</td>
            <td className="p-2">{settlement.generatedAt}</td>
            <td className="p-2">
              <div className="float-right whitespace-nowrap">
                <Link to={'/settlements/edit/' + settlement.id} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm">{t('settlement.list.edit')}</Link>
                <span> </span>
                <button type="button" onClick={() => confirmDelete(settlement.id!)} className="inline-block text-white bg-gray-500 hover:bg-gray-600 focus:ring-gray-200 focus:ring-3 rounded px-2.5 py-1.5 text-sm cursor-pointer">{t('settlement.list.delete')}</button>
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
