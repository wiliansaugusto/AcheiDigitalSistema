<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('tag_humanos', function (Blueprint $table) {
            $table->bigIncrements('idTag');
            $table->unsignedBigInteger('idProtegido');
            $table->foreign('idProtegido')->references('id_protegido_humano')
                ->on('protegidos_humano')->onDelete('cascade');

            $table->unsignedBigInteger('id_usuario');
            // Define fa oreign key
            $table->foreign('id_usuario')
                ->references('id_usuario')
                ->on('usuarios')
                ->onDelete('cascade');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('tag_humanos');
    }
};
