import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { ArticleItemComponent } from './article-item/article-item.component';
import { ArticleOverviewComponent } from './article-overview/article-overview.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'overview',
    pathMatch: 'full'
   },
   {
    path: 'overview',
    component: ArticleOverviewComponent
   },
   {
    path: 'article/:id',
    component: ArticleItemComponent
   },
   {
    path: 'login',
    component: LoginComponent
   }
 ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
