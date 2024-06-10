    package com.gamesmindt.myapplication.activities;

    import android.app.AlertDialog;
    import android.content.DialogInterface;
    import android.os.Bundle;
    import android.view.Window;
    import android.view.WindowManager;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.appcompat.widget.Toolbar;
    import androidx.fragment.app.Fragment;
    import androidx.fragment.app.FragmentTransaction;

    import java.util.ArrayList;
    import java.util.Arrays;

    import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
    import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
    import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

    import com.gamesmindt.myapplication.R;
    import com.gamesmindt.myapplication.fragments.HomeFragment;
    import com.gamesmindt.myapplication.fragments.ProfileFragment;

    public class MainActivity extends AppCompatActivity implements DuoMenuView.OnMenuClickListener, HomeFragment.OnMoreButtonClickListener  {
        private MenuAdapter mMenuAdapter;
        private ArrayList<String> mTitles = new ArrayList<>();
        private ViewHolder mViewHolder;
        Window window = null;
        @Override
        public void onMoreButtonClicked() {
            onOptionClicked(1, null);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView((int) R.layout.activity_main);

            this.mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menuOptions)));

            window = getWindow();

            // Inicializar las vistas
            this.mViewHolder = new ViewHolder();

            // Controlar acciones de la barra de herramientas
            handleToolbar();

            //Controlar las acciones del menú
            handleMenu();

            // Manejar acciones de cajón
            handleDrawer();

            // Mostrar fragmento principal en contenedor
            goToFragment(new com.gamesmindt.myapplication.fragments.HomeFragment(), true, "Home");
            this.mMenuAdapter.setViewSelected(0, true);
            setTitle(this.mTitles.get(0));

            ChangeStatusBarColor(R.color.statusbar_normal, R.drawable.bg_header_home);
        }


        public void onOptionClicked(int position, Object obj) {
            setTitle(this.mTitles.get(position));

            this.mMenuAdapter.setViewSelected(position, true);

            switch (position) {
                case 0:
                    goToFragment(new HomeFragment(), true, "Dashboard");
                    ChangeStatusBarColor(R.color.statusbar_normal, R.drawable.bg_header_home);
                    break;
                case 1:
                    goToFragment(new ProfileFragment(), true, "Ajustes");
                    ChangeStatusBarColor(R.color.statusbar_normal, R.drawable.bg_header_home);
                    break;
                default:
                    goToFragment(new HomeFragment(), false, "Dashboard");
                    ChangeStatusBarColor(R.color.statusbar_normal, R.drawable.bg_header_home);
                    break;
            }


            this.mViewHolder.mDuoDrawerLayout.closeDrawer();
        }

        public void ChangeStatusBarColor(int color, int toolbarBackgroundColor) {
            // Cambiar el color de la barra de estado
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(color));

            // Cambiar el color del header nav
            mViewHolder.mToolbar.setBackgroundResource(toolbarBackgroundColor);
        }

        public void onBackPressed() {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);

            if (!(currentFragment instanceof HomeFragment)) {
                super.onBackPressed();
            }

            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                getSupportFragmentManager().popBackStack();
                return;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Estás seguro de que quieres salir?").setCancelable(false).setPositiveButton("Salir de la App", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.create().show();
        }

        private void handleToolbar() {
            setSupportActionBar(this.mViewHolder.mToolbar);
        }

        private void handleDrawer() {
            DuoDrawerToggle duoDrawerToggle = new DuoDrawerToggle(this, this.mViewHolder.mDuoDrawerLayout, this.mViewHolder.mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            this.mViewHolder.mDuoDrawerLayout.setDrawerListener(duoDrawerToggle);
            duoDrawerToggle.syncState();
        }

        private void handleMenu() {
            mMenuAdapter = new MenuAdapter(mTitles);
            mViewHolder.mDuoMenuView.setOnMenuClickListener(this);
            mViewHolder.mDuoMenuView.setAdapter(mMenuAdapter);
        }

        private void goToFragment(Fragment fragment, boolean z, String str) {
            FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();

            if (z) {
                beginTransaction.addToBackStack(str);
            }

            beginTransaction.replace(R.id.container, fragment).commit();
        }

        public void onFooterClicked() {
        }

        public void onHeaderClicked() {
        }

        private class ViewHolder {
            public final DuoDrawerLayout mDuoDrawerLayout;
            public final DuoMenuView mDuoMenuView;
            public final Toolbar mToolbar;

            ViewHolder() {
                DuoDrawerLayout duoDrawerLayout = (DuoDrawerLayout) MainActivity.this.findViewById(R.id.drawer);
                this.mDuoDrawerLayout = duoDrawerLayout;
                this.mDuoMenuView = (DuoMenuView) duoDrawerLayout.getMenuView();
                this.mToolbar = (Toolbar) MainActivity.this.findViewById(R.id.toolbar);
            }
        }
    }
