import React from 'react';
import { createBrowserRouter, RouterProvider } from 'react-router';
import App from "./app";
import Home from './home/home';
import UserList from './user/user-list';
import UserAdd from './user/user-add';
import UserEdit from './user/user-edit';
import RoleList from './role/role-list';
import RoleAdd from './role/role-add';
import RoleEdit from './role/role-edit';
import AccountList from './account/account-list';
import AccountAdd from './account/account-add';
import AccountEdit from './account/account-edit';
import TransactionList from './transaction/transaction-list';
import TransactionAdd from './transaction/transaction-add';
import TransactionEdit from './transaction/transaction-edit';
import FraudAlertList from './fraud-alert/fraud-alert-list';
import FraudAlertAdd from './fraud-alert/fraud-alert-add';
import FraudAlertEdit from './fraud-alert/fraud-alert-edit';
import KycDocumentList from './kyc-document/kyc-document-list';
import KycDocumentAdd from './kyc-document/kyc-document-add';
import KycDocumentEdit from './kyc-document/kyc-document-edit';
import SettlementList from './settlement/settlement-list';
import SettlementAdd from './settlement/settlement-add';
import SettlementEdit from './settlement/settlement-edit';
import Error from './error/error';


export default function AppRoutes() {
  const router = createBrowserRouter([
    {
      element: <App />,
      children: [
        { path: '', element: <Home /> },
        { path: 'users', element: <UserList /> },
        { path: 'users/add', element: <UserAdd /> },
        { path: 'users/edit/:id', element: <UserEdit /> },
        { path: 'roles', element: <RoleList /> },
        { path: 'roles/add', element: <RoleAdd /> },
        { path: 'roles/edit/:id', element: <RoleEdit /> },
        { path: 'accounts', element: <AccountList /> },
        { path: 'accounts/add', element: <AccountAdd /> },
        { path: 'accounts/edit/:id', element: <AccountEdit /> },
        { path: 'transactions', element: <TransactionList /> },
        { path: 'transactions/add', element: <TransactionAdd /> },
        { path: 'transactions/edit/:id', element: <TransactionEdit /> },
        { path: 'fraudAlerts', element: <FraudAlertList /> },
        { path: 'fraudAlerts/add', element: <FraudAlertAdd /> },
        { path: 'fraudAlerts/edit/:id', element: <FraudAlertEdit /> },
        { path: 'kycDocuments', element: <KycDocumentList /> },
        { path: 'kycDocuments/add', element: <KycDocumentAdd /> },
        { path: 'kycDocuments/edit/:id', element: <KycDocumentEdit /> },
        { path: 'settlements', element: <SettlementList /> },
        { path: 'settlements/add', element: <SettlementAdd /> },
        { path: 'settlements/edit/:id', element: <SettlementEdit /> },
        { path: 'error', element: <Error /> },
        { path: '*', element: <Error /> }
      ]
    }
  ]);

  return (
    <RouterProvider router={router} />
  );
}
